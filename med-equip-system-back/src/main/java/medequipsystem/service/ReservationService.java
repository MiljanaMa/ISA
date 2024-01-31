package medequipsystem.service;

import com.google.zxing.*;
import com.google.zxing.client.j2se.BufferedImageLuminanceSource;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import medequipsystem.domain.*;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.repository.AppointmentRepository;
import medequipsystem.repository.CompanyEquipmentRepository;
import medequipsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.orm.ObjectOptimisticLockingFailureException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;
import java.util.stream.Collectors;


@Service
public class ReservationService {
    @Autowired
    private ReservationRepository reservationRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CompanyEquipmentRepository equipmentRepository;
    //morala sam da uradim ovo, bice hibridne klase nazalost
    @Autowired
    private AppointmentService appointmentService;


    @Transactional(readOnly = false)
    public Reservation createPredefined(Appointment appointment, Set<ReservationItem> reservationItems, Client client) throws ObjectOptimisticLockingFailureException, Exception {
        try {
            if(reservationRepository.findByAppointmentIdAndClientId(appointment.getId(), client.getId()) != null){
                throw new Exception("You've already tried to reserve this appointment.");
            }
            Appointment availableAppointment = appointmentRepository.getAvailableById(appointment.getId(), AppointmentStatus.AVAILABLE);
            if (availableAppointment == null)
                throw new Exception("Appointment is reserved");
            Set<ReservationItem> updatedItems = getEquipmentForReservation(reservationItems);
            if (updatedItems == null)
                throw new Exception("Not enough equipment in storage");
            availableAppointment.setStatus(AppointmentStatus.RESERVED);
            //odmah po referenci azurira
            Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, availableAppointment, updatedItems);

            return reservationRepository.save(reservation);
        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ObjectOptimisticLockingFailureException("Appointment is not available anymore", e.getCause());
        } catch(Exception e){
            throw new Exception("Ooops problem (You've already tried to reserve this appointment or there is not enough equipment in storage.) ", e.getCause());
        }
    }

    public Reservation getById(Long id) {
        Optional<Reservation> reservationOptional = this.reservationRepository.findById(id);
        return reservationOptional.orElse(null);
    }


    public Set<Reservation> getReservationsInProgress() {

        return reservationRepository.getReservationsInProgress();
    }

    //@Transactional(readOnly = false)
    public Reservation createCustom(Appointment appointment, Set<ReservationItem> reservationItems, Client client) throws Exception {
        try {
            Set<ReservationItem> updatedItems = getEquipmentForReservation(reservationItems);
            if (updatedItems == null)
                throw new Exception("Not enough equipment in storage");
            Company company = updatedItems.stream().findFirst().get().getEquipment().getCompany();
            Set<CompanyAdmin> availableAdmins = appointmentService.isCustomAppoinmentAvailable(company, appointment.getDate(), appointment.getStartTime());
            if (availableAdmins.isEmpty())
                throw new Exception("Appointment is not available anymore");
            Appointment newAppointment = new Appointment(0L, appointment.getDate(), appointment.getStartTime(), appointment.getEndTime(), AppointmentStatus.RESERVED, availableAdmins.stream().findFirst().get());
            Appointment savedAppointment = appointmentRepository.save(newAppointment);
            Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, savedAppointment, updatedItems);
            return reservationRepository.save(reservation);

        } catch (ObjectOptimisticLockingFailureException e) {
            throw new ObjectOptimisticLockingFailureException("Available count is changed, please try again.", e.getCause());
        } catch (Exception e) {
            throw new Exception(e.getMessage(), e.getCause());
        }
    }

    public Set<Reservation> getUserReservations(Long id) {
        //stavi da sortira
        List<Reservation> reservations = reservationRepository.findAll();
        Set<Reservation> userReservations = reservations.stream()
                .filter(reservation -> reservation.getClient().getUser().getId().equals(id))
                .collect(Collectors.toSet());
        return userReservations;
    }

    public Set<Reservation> getReservationsByAdminId(Long adminId){
        return reservationRepository.findReservationsByAdminId(adminId);
    }

    public void requestTaking(Long reservationId) {
        Reservation reservation = getById(reservationId);
        reservation.setStatus(ReservationStatus.TAKING_REQUESTED);
        this.reservationRepository.save(reservation);
    }

    public Set<ReservationItem> getEquipmentForReservation(Set<ReservationItem> reservationItems) {
        //dok ja provjeravam neko vrslja po bazi, ali version rijesi taj problem
        CompanyEquipment ce;
        Set<ReservationItem> updatedItems = new HashSet<>();

        for (ReservationItem ri : reservationItems) {
            //exception not handeled
            ce = equipmentRepository.getById(ri.getEquipment().getId());
            if(ce == null)
                return null;
            if (ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            updatedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce, ri.getCount() * ce.getPrice()));
        }
        return updatedItems;
    }

    //potentionally move this to utils
    public byte[] generateQRCode(String qrData) {
        try {
            QRCodeWriter qrCodeWriter = new QRCodeWriter();
            Hashtable<EncodeHintType, ErrorCorrectionLevel> hintMap = new Hashtable<>();
            hintMap.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.L);
            BitMatrix bitMatrix = qrCodeWriter.encode(qrData, BarcodeFormat.QR_CODE, 300, 300, hintMap);

            BufferedImage qrImage = new BufferedImage(300, 300, BufferedImage.TYPE_INT_RGB);
            qrImage.createGraphics();

            for (int x = 0; x < 300; x++) {
                for (int y = 0; y < 300; y++) {
                    qrImage.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
                }
            }

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            ImageIO.write(qrImage, "png", baos);
            return baos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
            return new byte[0];
        }
    }

    public byte[] getQrCode(Reservation reservation) {
        String qrData = "Reservation details: \n"
                + "- Reservation id: " + reservation.getId() + "\n"
                + "- Appointment date: " + reservation.getAppointment().getDate() + "\n"
                + "- Appointment time: " + reservation.getAppointment().getStartTime()
                + "-" + reservation.getAppointment().getEndTime() + "\n"
                + "- Reservation items: \n";

        for (ReservationItem item : reservation.getReservationItems()) {
            qrData += "  -> " + item.getEquipment().getName() + ", Count: [" + item.getCount() + "], Price: [" + item.getPrice() + "]\n";
        }
        return generateQRCode(qrData);
    }

    public void cancel(Long id) { //change void to something else
        Reservation reservation = getById(id);
        CompanyEquipment ce;
        for (ReservationItem ri : reservation.getReservationItems()) {
            ce = this.equipmentRepository.getReferenceById(ri.getEquipment().getId());
            ;
            ce.setReservedCount(ce.getReservedCount() - ri.getCount());
        }
        reservation.setStatus(ReservationStatus.CANCELLED);
        this.reservationRepository.save(reservation);

        Appointment appointment = reservation.getAppointment();
        appointment.setStatus(AppointmentStatus.AVAILABLE);
        this.appointmentRepository.save(appointment);
    }

    public boolean isReservationExpired(LocalDate appointmentDate, LocalTime appointmentTime) {
        LocalDateTime appointmentDateTime = LocalDateTime.of(appointmentDate, appointmentTime);
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(appointmentDateTime);
    }

    public boolean isReservationAppointmentWithinToday(LocalDate appointmentDate, LocalTime appointmentStartTime, LocalTime appointmentEndTime) {
        LocalDateTime appointmentStartDateTime = LocalDateTime.of(appointmentDate, appointmentStartTime);
        LocalDateTime appointmentEndDateTime = LocalDateTime.of(appointmentDate, appointmentEndTime);
        LocalDateTime currentDateTime = LocalDateTime.now();
        return currentDateTime.isAfter(appointmentStartDateTime) && currentDateTime.isBefore(appointmentEndDateTime);
    }

    public void updateStatus(Reservation reservation, ReservationStatus status){
        reservation.setStatus(status);
        this.reservationRepository.save(reservation);
    }

    public void take(Long reservationId) {
        Reservation reservation = getById(reservationId);
        CompanyEquipment ce;
        for (ReservationItem ri : reservation.getReservationItems()) {
            ce = this.equipmentRepository.getReferenceById(ri.getEquipment().getId());
            ;
            ce.setReservedCount(ce.getReservedCount() - ri.getCount()); //oduzmi sa stanja rezervisanih
            ce.setCount(ce.getCount() - ri.getCount()); //oduzmi sa stanja dostupnih
            this.equipmentRepository.save(ce);
        }
        reservation.setStatus(ReservationStatus.TAKEN);
        this.reservationRepository.save(reservation);
    }

    public String processQRCode(MultipartFile file) {
        try {
            BufferedImage qrImage = ImageIO.read(file.getInputStream());
            LuminanceSource source = new BufferedImageLuminanceSource(qrImage);
            BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
            Result result = new MultiFormatReader().decode(bitmap);
            return result.getText();
        } catch (Exception e) {
            throw new RuntimeException("Error processing QR code");
        }
    }

    public Long extractReservationId(String reservationDetails) {
        try {
            int startIndex = reservationDetails.indexOf("Reservation id:") + "Reservation id:".length();
            int endIndex = reservationDetails.indexOf("\n", startIndex);

            String idString = reservationDetails.substring(startIndex, endIndex).trim();
            return Long.parseLong(idString);
        } catch (Exception e) {
            throw new RuntimeException("Error extracting reservation id");
        }
    }
}

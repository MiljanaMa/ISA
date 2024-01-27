package medequipsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import medequipsystem.domain.*;
import medequipsystem.domain.enums.AppointmentStatus;
import medequipsystem.domain.enums.ReservationStatus;
import medequipsystem.dto.CustomAppointmentDTO;
import medequipsystem.repository.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
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


    //Transactional
    public Reservation createPredefined(Appointment appointment, Set<ReservationItem> reservationItems, Client client){
        Appointment availableAppointment = appointmentRepository.getAvailableById(appointment.getId(), AppointmentStatus.AVAILABLE);
        if( availableAppointment == null)
            new Exception("Appointment is reserved");
        Set<ReservationItem> updatedItems = checkEquipment(reservationItems);
        if( updatedItems == null)
            new Exception("Not enough equipment in storage");
        availableAppointment.setStatus(AppointmentStatus.RESERVED);
        //odmah po referenci azurira
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, availableAppointment, updatedItems);
        //da li spring sam odradi update, ako odradi i za equipment
        //appointmentRepository.save(availableAppointment);

        //da li on lockuje sve tabele zajedno ako ima asocijacije ka njima
        return reservationRepository.save(reservation);
    }

    //potentialy move to equipment service
    private Set<ReservationItem> checkEquipment(Set<ReservationItem> reservationItems) {
        //dok ja provjeravam neko vrslja po bazi, ali version rijesi taj problem
        CompanyEquipment ce;
        Set<ReservationItem> updatedItems = new HashSet<>();
        for(ReservationItem ri: reservationItems){
            //exception not handeled
            ce = equipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            updatedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce, ri.getCount()*ce.getPrice()));
        }
        return updatedItems;
    }


    public Set<Reservation> getReservationsInProgress(){

        return reservationRepository.getReservationsInProgress();
    }

    public Reservation createCustom(Appointment appointment, Set<ReservationItem> reservationItems, Client client){
        //move this
        Set<ReservationItem> updatedItems = checkEquipment(reservationItems);
        if( updatedItems == null)
            new Exception("Not enough equipment in storage");
        Company company = updatedItems.stream().findFirst().get().getEquipment().getCompany();
        Set<CompanyAdmin> availableAdmins = appointmentService.isCustomAppoinmentAvailable(company,appointment.getDate(), appointment.getStartTime());
        if(availableAdmins.isEmpty())
            new Exception("Appointment is not available anymore");
        //do here to find available admin
        //treba ubaciti metodu koja radi check da li je termin i dalje slobodan
        Appointment newAppointment = new Appointment(0L, appointment.getDate(), appointment.getStartTime(), appointment.getEndTime(), AppointmentStatus.RESERVED, availableAdmins.stream().findFirst().get());
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, savedAppointment, updatedItems);
        return reservationRepository.save(reservation);
    }
    public Set<Reservation> getUserReservations(Long id){
        //stavi da sortira
        List<Reservation> reservations = reservationRepository.findAll();
        Set<Reservation> userReservations = reservations.stream()
                .filter(reservation -> reservation.getClient().getUser().getId().equals(id))
                .collect(Collectors.toSet());
        return userReservations;
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
    public byte[] getQrCode(Reservation reservation){
        String qrData = "Reservation details: \n"
                + "- Appointment date: " + reservation.getAppointment().getDate() + "\n"
                + "- Appointment time: " + reservation.getAppointment().getStartTime()
                + "-" + reservation.getAppointment().getEndTime() + "\n"
                + "- Reservation items: \n";

        for(ReservationItem item: reservation.getReservationItems()){
            qrData += "  -> " + item.getEquipment().getName() + ", Count: [" + item.getCount() + "], Price: [" + item.getPrice() +"]\n";
        }
        return generateQRCode(qrData);
    }
}

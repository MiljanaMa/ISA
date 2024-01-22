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
    private ClientRepository clientRepository;
    @Autowired
    private AppointmentRepository appointmentRepository;
    @Autowired
    private CompanyEquipmentRepository equipmentRepository;


    public Reservation createPredefined(Appointment appointment, Set<ReservationItem> reservationItems, Client client){
        Optional<Appointment> appointmentOptional = appointmentRepository.findById(appointment.getId());
        if( appointmentOptional == null)
            return null;
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, appointment, reservationItems);
        //korak za smanjivanje kolicine robe
        CompanyEquipment ce;
        Set<ReservationItem> changedItems = new HashSet<>();
        for(ReservationItem ri: reservation.getReservationItems()){
            ce = equipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            changedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce, ri.getCount()*ce.getPrice()));
        }
        reservation.setReservationItems(changedItems);

        Appointment existingAppointment = appointmentOptional.get();
        existingAppointment.setStatus(AppointmentStatus.RESERVED);
        appointmentRepository.save(existingAppointment);

        return reservationRepository.save(reservation);
    }


    public Set<Reservation> getReservationsInProgress(){

        return reservationRepository.getReservationsInProgress();
    }

    public Reservation createCustom(CustomAppointmentDTO appointment, Set<ReservationItem> reservationItems, Client client, Set<CompanyAdmin> admins){
        Reservation reservation = new Reservation(0L, ReservationStatus.RESERVED, client, new Appointment(), reservationItems);
        //korak za smanjivanje kolicine robe
        CompanyEquipment ce;
        Set<ReservationItem> changedItems = new HashSet<>();
        for(ReservationItem ri: reservation.getReservationItems()){
            ce = equipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            changedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce, ri.getCount()*ce.getPrice()));
        }
        reservation.setReservationItems(changedItems);

        Appointment newAppointment = new Appointment(0L, appointment.getDate(), appointment.getStartTime(), appointment.getEndTime(), AppointmentStatus.RESERVED, admins.stream().findFirst().get());
        Appointment savedAppointment = appointmentRepository.save(newAppointment);
        reservation.setAppointment(savedAppointment);
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

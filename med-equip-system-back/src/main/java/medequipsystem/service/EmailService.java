package medequipsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import medequipsystem.domain.Reservation;
import medequipsystem.domain.ReservationItem;
import medequipsystem.util.EmailToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.core.io.Resource;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.imageio.ImageIO;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.util.Hashtable;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private Environment env;

    @Autowired
    public EmailService(JavaMailSender mailSender, Environment environment) {
        this.javaMailSender = mailSender;
        this.env = environment;
    }

    @Async
    public void sendConfirmationMail(EmailToken emailToken ) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailToken.getClient().getUser().getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration");
        mail.setText("Hey, " + emailToken.getClient().getUser().getFirstName() + ",\nYou are almost there. "
                + "Click the link below to confirm your email and finish creating your account:\n"
                + "http://localhost:8092/api/auth/confirm?token=" + emailToken.getToken());

        javaMailSender.send(mail);
    }

    @Async
    public void sendReservationMail(String email, Reservation reservation) {
        String qrData = "Reservation details: \n"
                + "- Appointment date: " + reservation.getAppointment().getDate() + "\n"
                + "- Appointment time: " + reservation.getAppointment().getStartTime()
                + "-" + reservation.getAppointment().getEndTime() + "\n"
                + "- Reservation items: \n";

        for(ReservationItem item: reservation.getReservationItems()){
            qrData += "  -> " + item.getEquipment().getName() + ", Count: [" + item.getCount() + "]\n";
        }

        byte[] qrImageBytes = generateQRCode(qrData);

        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);

            helper.setTo(email);
            helper.setSubject("Reservation Confirmation");
            helper.setText("Your reservation has been succesfully created. Here is QR code with reservation details.");

            Resource qrImageResource = new ByteArrayResource(qrImageBytes);
            helper.addAttachment("QR_Code.png", qrImageResource);

            javaMailSender.send(message);

            System.out.println("Email sent successfully with QR code");
        } catch (MessagingException | MailException e) {
            e.printStackTrace();
            System.out.println("Failed to send email with QR code");
        }
    }

    private byte[] generateQRCode(String qrData) {
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

}

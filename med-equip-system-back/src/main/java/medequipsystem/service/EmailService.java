package medequipsystem.service;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.EncodeHintType;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.qrcode.QRCodeWriter;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import medequipsystem.domain.CompanyEquipment;
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
    public void sendReservationMail(String email, byte[] qrImageBytes) {
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

    @Async
    public void sendEquipmentPickupConfirmationMail(String email, Reservation reservation) {
        try {
            MimeMessage message = javaMailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true);
            helper.setTo(email);
            helper.setSubject("Equipment Pickup Confirmation");
            helper.setText(buildEquipmentPickupConfirmationMessage(reservation));
            javaMailSender.send(message);
            System.out.println("Email sent successfully for equipment pickup confirmation");
        } catch (MessagingException | MailException e) {
            System.out.println("Failed to send email for equipment pickup confirmation");
        }
    }

    private String buildEquipmentPickupConfirmationMessage(Reservation reservation) {
        StringBuilder message = new StringBuilder();
        message.append("You have successfully picked up the equipment!\n");
        message.append("Reservation ID: ").append(reservation.getId()).append("\n");
        message.append("Equipment list:\n");
        for (ReservationItem reservationItem : reservation.getReservationItems()) {
            CompanyEquipment equipment = reservationItem.getEquipment();
            message.append("- ").append(equipment.getName()).append(", Quantity: ").append(reservationItem.getCount())
                    .append(", Price per piece: ").append(equipment.getPrice()).append("\n");
        }
        return message.toString();
    }


}

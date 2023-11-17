package medequipsystem.service;

import medequipsystem.domain.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class EmailService {
    private JavaMailSender javaMailSender;
    private Environment env;

    @Autowired
    public EmailService(JavaMailSender mailSenderail, Environment environment) {
        this.javaMailSender = mailSenderail;
        this.env = environment;
    }

    @Async
    public void sendMail(User user) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(user.getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration");
        mail.setText("Hey, " + user.getFirstName() + ",\nYou are almost there. " +
                "Click the link below to confirm your email and finish creating your account: "
                + "\n http://localhost:4200/profile/" + user.getId());
        javaMailSender.send(mail);
    }

}

package medequipsystem.service;

import medequipsystem.domain.User;
import medequipsystem.token.EmailToken;
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
    public EmailService(JavaMailSender mailSender, Environment environment) {
        this.javaMailSender = mailSender;
        this.env = environment;
    }

    @Async
    public void sendMail(EmailToken emailToken ) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(emailToken.getUser().getEmail());
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration");
        mail.setText("Hey, " + emailToken.getUser().getFirstName() + ",\nYou are almost there. "
                + "Click the link below to confirm your email and finish creating your account:\n"
                + "http://localhost:8092/api/users/confirm?token=" + emailToken.getToken());

        javaMailSender.send(mail);
    }

}

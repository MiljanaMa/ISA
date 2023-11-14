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
    public void sendMail(String userMail) throws MailException {
        SimpleMailMessage mail = new SimpleMailMessage();
        mail.setTo(userMail);
        mail.setFrom(env.getProperty("spring.mail.username"));
        mail.setSubject("Registration");
        mail.setText("You have been successfully registered.");
        javaMailSender.send(mail);
    }

}

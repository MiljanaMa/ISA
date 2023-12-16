package medequipsystem.service;

import medequipsystem.repository.EmailTokenRepository;
import medequipsystem.token.EmailToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EmailTokenService {
    @Autowired
    private EmailTokenRepository emailTokenRepository;

    public EmailToken getByToken(String token){
        Optional<EmailToken> emailToken = this.emailTokenRepository.findByToken(token);
        return emailToken.orElse(null);
    }

    public EmailToken create(EmailToken emailToken){
        return this.emailTokenRepository.save(emailToken);
    }
}

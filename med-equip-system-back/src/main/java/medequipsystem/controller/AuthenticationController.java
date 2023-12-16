package medequipsystem.controller;

import javax.servlet.http.HttpServletResponse;

import medequipsystem.domain.Client;
import medequipsystem.dto.auth.ClientRegistrationDTO;
import medequipsystem.service.ClientService;
import medequipsystem.service.EmailService;
import medequipsystem.service.EmailTokenService;
import medequipsystem.util.EmailToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import medequipsystem.service.UserService;
import medequipsystem.util.TokenUtils;
import medequipsystem.dto.auth.TokenDto;
import medequipsystem.domain.User;
import medequipsystem.dto.auth.LoginDto;

import java.security.Principal;

@RestController
@RequestMapping(value = "api/auth")
public class AuthenticationController {
    @Autowired
    private TokenUtils tokenUtils;
    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private UserService userService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private EmailTokenService emailTokenService;
    private PasswordEncoder passwordEncoder;
    @Autowired
    EmailService emailService;

    @PostMapping("/register")
    public ResponseEntity<Client> addUser(@RequestBody ClientRegistrationDTO clientDto) {

       Client client = this.clientService.create(clientDto);

        if(client == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EmailToken emailToken = new EmailToken(client);
        emailToken = emailTokenService.create(emailToken);

        try {
            emailService.sendConfirmationMail(emailToken);
        }catch( Exception e ){
            System.out.println("Error (sanding mail): " + e.getMessage());
        }

        return new ResponseEntity<>(client, HttpStatus.CREATED);
    }


    @PostMapping("/login")
    public ResponseEntity<TokenDto> createAuthenticationToken(@RequestBody LoginDto loginDto, HttpServletResponse response) {

        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getEmail(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        User user = (User) authentication.getPrincipal();
        String jwt = tokenUtils.generateToken(user.getUsername());
        int expiresIn = tokenUtils.getExpiredIn();

        return ResponseEntity.ok(new TokenDto(jwt, expiresIn));
    }


    @GetMapping("/whoami")
    @PreAuthorize("hasAnyRole('CLIENT', 'SYSADMIN', 'COMPADMIN')")
    public User user(Principal user) {
        return this.userService.getByEmail(user.getName());
    }

    @GetMapping (value = "/confirm")
    public ResponseEntity<String> confirmEmail(@RequestParam("token") String token){
        EmailToken emailToken = this.emailTokenService.getByToken(token);
        Client client = emailToken.getClient();
        clientService.confirmEmail(client);

        return  new ResponseEntity<>(generateResponse(), HttpStatus.OK);
    }

    public String generateResponse(){
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<p>You have succesfully confirmed your email address and activated your account.</p>" +
                "<p>Here is the link to login page: </p>" +
                "<a href= \"http://localhost:4200/login" +"\">Go to Login Page</a>" +
                "</body>" +
                "</html>";
    }

}

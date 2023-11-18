package medequipsystem.controller;

import medequipsystem.domain.User;
import medequipsystem.dto.UserDTO;
import medequipsystem.domain.enums.UserType;
import medequipsystem.service.EmailService;
import medequipsystem.service.EmailTokenService;
import medequipsystem.service.UserService;
import medequipsystem.token.EmailToken;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200/")
@RestController
@RequestMapping(value = "api/users")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private EmailService emailService;
    @Autowired
    EmailTokenService emailTokenService;
    private Logger logger = LoggerFactory.getLogger(UserController.class);

    @GetMapping
    public ResponseEntity<List<UserDTO>> getAll() {
        List<User> users = userService.getAll();;
        List<UserDTO> usersDTO = new ArrayList<>();
        for (User u : users) {
            usersDTO.add(new UserDTO(u));
        }
        return new ResponseEntity<>(usersDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<UserDTO> getById(@PathVariable Long id) {
        UserDTO userDTO = new UserDTO(userService.getById(id));
        if(userDTO.isEmailConfirmed()) {
            return new ResponseEntity<>(userDTO, HttpStatus.OK);
        }
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }

    @PostMapping
    public ResponseEntity<UserDTO> register(@RequestBody UserDTO userDTO) {

        User user = mapDtoToDomain(userDTO);
        user = userService.create(user);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        EmailToken emailToken = new EmailToken(user);
        emailToken = emailTokenService.create(emailToken);

        try {
            emailService.sendMail(emailToken);
        }catch( Exception e ){
            logger.info("Error (sanding mail): " + e.getMessage());
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }

    @GetMapping (value = "/confirm")
    public ResponseEntity<String>  confirm(@RequestParam("token") String token){
        EmailToken emailToken = this.emailTokenService.getByToken(token);
        User user = emailToken.getUser();
        user.setEmailConfirmed(true);
        userService.update(user);

        return  new ResponseEntity<>(generateResponse(user.getId()), HttpStatus.OK);
    }

    public String generateResponse(Long userId){
        return "<!DOCTYPE html>" +
                "<html>" +
                "<body>" +
                "<p>You have succesfully confirmed your email address and activated your account.</p>" +
                "<p>Here is the link to your profile: </p>" +
                "<a href= \"http://localhost:4200/profile/" + userId + "\">Go to Profile</a>" +
                "</body>" +
                "</html>";
    }

    public User mapDtoToDomain(UserDTO userDTO){
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setJobTitle(userDTO.getJobTitle());
        user.setHospitalInfo(userDTO.getHospitalInfo());
        user.setUserType(UserType.CUSTOMER);
        user.setEmailConfirmed(false);
        return user;
    }
}

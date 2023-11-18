package medequipsystem.controller;

import medequipsystem.domain.User;
import medequipsystem.domain.enums.LoyaltyType;
import medequipsystem.dto.UserDTO;
import medequipsystem.domain.enums.UserType;
import medequipsystem.service.EmailService;
import medequipsystem.service.UserService;
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
        return new ResponseEntity<>(userDTO, HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<UserDTO> create(@RequestBody UserDTO userDTO) {
        User user = new User();
        user.setEmail(userDTO.getEmail());
        user.setPassword(userDTO.getPassword());
        user.setFirstName(userDTO.getFirstName());
        user.setLastName(userDTO.getLastName());
        user.setCity(userDTO.getCity());
        user.setCountry(userDTO.getCountry());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setJobTitle(userDTO.getJobTitle());
        user.setCompanyInformation(userDTO.getCompanyInformation());
        user.setUserType(UserType.CUSTOMER);
        user.setLoyaltyType(LoyaltyType.NONE);
        user.setPenalPoints(0);

        user = userService.create(user);

        if(user == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        }

        try {
            emailService.sendMail(userDTO.getEmail());
        }catch( Exception e ){
            logger.info("Error (sanding mail): " + e.getMessage());
        }

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }
    @PutMapping("/update")
    public ResponseEntity<UserDTO> update(@RequestBody User user) {
        user = userService.update(user);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<UserDTO> updatePassword(@PathVariable long userId,  @RequestBody String password) {
        User user = userService.updatePassword(userId, password);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new UserDTO(user), HttpStatus.OK);
    }
}

package medequipsystem.controller;

import medequipsystem.domain.User;
import medequipsystem.dto.UserDTO;
import medequipsystem.enums.UserType;
import medequipsystem.service.UserService;
import org.hibernate.query.QueryParameter;
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
    public ResponseEntity<UserDTO> getAll(@PathVariable Long id) {
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

        user = userService.create(user);
        return new ResponseEntity<>(new UserDTO(user), HttpStatus.CREATED);
    }
}

package medequipsystem.controller;

import medequipsystem.domain.Client;
import medequipsystem.domain.LoyaltyProgram;
import medequipsystem.dto.ClientDTO;
import medequipsystem.service.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clients")
public class ClientController {

    @Autowired
    private LoyaltyProgramService loyaltyService;
    @Autowired
    private ClientService clientService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<Client> client = clientService.getAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();
        for (Client c : client) {
            clientsDTO.add(new ClientDTO(c));
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<ClientDTO> getById(@PathVariable Long id) {
        ClientDTO clientDTO = new ClientDTO(clientService.getById(id));
        if(clientDTO == null || !clientDTO.isEmailConfirmed()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        LoyaltyProgram loyaltyProgram = loyaltyService.getUserLoyaltyType(clientDTO.getPoints(), clientDTO.getPenaltyPoints());
        if(loyaltyProgram == null){
            clientDTO.setLoyaltyType("NONE");
            clientDTO.setDiscount(0);
        }else{
            clientDTO.setLoyaltyType(loyaltyProgram.getLoyaltyType());
            clientDTO.setDiscount(loyaltyProgram.getDiscount());
        }
        return new ResponseEntity<>(clientDTO, HttpStatus.OK);
    }

/*
    @PutMapping("/update")
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO) {
        client = clientService.update(clientDTO);
        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ClientDTO(user), HttpStatus.OK);
    }


    @PutMapping("/updatePassword/{userId}")
    public ResponseEntity<ClientDTO> updatePassword(@PathVariable long userId, @RequestBody String password) {
        User user = userService.updatePassword(userId, password);

        if(user == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ClientDTO(user), HttpStatus.OK);
    }

    @PostMapping
    public ResponseEntity<ClientDTO> register(@RequestBody ClientDTO userDTO) {

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

        return new ResponseEntity<>(new ClientDTO(user), HttpStatus.CREATED);
    }

    @GetMapping (value = "/confirm")
    public ResponseEntity<String>  confirm(@RequestParam("token") String token){
        EmailToken emailToken = this.emailTokenService.getByToken(token);
        User user = emailToken.getUser();
        userService.setEmailAsConfirmed(user);

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
    public Client mapDtoToDomain(ClientDTO clientDTO) {
        User user = new User();
        user.setEmail(clientDTO.getEmail());
        user.setPassword(clientDTO.getPassword());
        user.setFirstName(clientDTO.getFirstName());
        user.setLastName(clientDTO.getLastName());
        user.setCity(clientDTO.getCity());
        user.setCountry(clientDTO.getCountry());
        user.setPhoneNumber(clientDTO.getPhoneNumber());
        user.setUserType(UserType.CUSTOMER);

        Client client = new Client();
        client.setJobTitle(clientDTO.getJobTitle());
        client.setHospitalInfo(clientDTO.getHospitalInfo());
        client.setEmailConfirmed(false);
        client.setPenaltyPoints(0);
        client.setPoints(0);
        client.setUser(user);
        return client;
    }

     */
}

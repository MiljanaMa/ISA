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
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "api/clients")
public class ClientController {

    @Autowired
    private LoyaltyProgramService loyaltyService;
    @Autowired
    private ClientService clientService;
    @Autowired
    private UserService userService;

    @GetMapping
    public ResponseEntity<List<ClientDTO>> getAll() {
        List<Client> client = clientService.getAll();
        List<ClientDTO> clientsDTO = new ArrayList<>();
        for (Client c : client) {
            clientsDTO.add(new ClientDTO(c));
        }
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @GetMapping(value = "/getCurrent")
    public ResponseEntity<ClientDTO> getCurrentClient(Principal user) {
        Long userId = userService.getByEmail(user.getName()).getId();
        ClientDTO clientDTO = new ClientDTO(clientService.getByUserId(userId));
        //if(clientDTO == null || !clientDTO.isEmailConfirmed()) return new ResponseEntity<>(HttpStatus.NOT_FOUND);

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

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/update")
    public ResponseEntity<ClientDTO> update(@RequestBody ClientDTO clientDTO) {
        Client client = clientService.update(clientDTO);
        if(client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/updatePassword")
    // password is now hash value, checking the old password on the frontend doesn't make any sense
    // now it works just when hash is entered as an old password on frontend, change this
    // AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA PLAKYYYYYYYYYYYYYYYYYYYYYYYY
    // something wrong when password changes - it goes craazyy
    public ResponseEntity<ClientDTO> updatePassword(@RequestBody String password, Principal user) {
        Long userId = userService.getByEmail(user.getName()).getId();
        Client client = clientService.updatePassword(userId, password);
        if(client == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(new ClientDTO(client), HttpStatus.OK);
    }


/*
ADD THIS SHIT
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
                "<p>Here is the link to login page: </p>" +
                "<a href= \"http://localhost:4200/login" + userId + "\">Go to Login</a>" +
                "</body>" +
                "</html>";
    }
     */
}

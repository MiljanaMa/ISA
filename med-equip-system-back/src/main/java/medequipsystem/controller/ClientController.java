package medequipsystem.controller;

import medequipsystem.domain.Client;
import medequipsystem.domain.LoyaltyProgram;
import medequipsystem.dto.ClientDTO;
import medequipsystem.dto.CompanyDTO;
import medequipsystem.dto.PasswordChangeDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.*;
import org.apache.coyote.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.yaml.snakeyaml.comments.CommentLine;

import java.security.Principal;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

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

    @GetMapping("/byAdmin/{id}")
    public ResponseEntity<Set<ClientDTO>> getByAdmin(@PathVariable Long id){
        Set<Client> clients = clientService.getByAdminId(id);
        Set<ClientDTO> clientsDTO = clients.stream().map(ClientDTO::new).collect(Collectors.toSet());;
        return new ResponseEntity<>(clientsDTO, HttpStatus.OK);
    }

    @PreAuthorize("hasRole('CLIENT')")
    @PostMapping("/updatePassword")
    public ResponseEntity<ClientDTO> updatePassword(@RequestBody PasswordChangeDTO passwords, Principal user) {
        Long userId = userService.getByEmail(user.getName()).getId();
        boolean isOldPasswordCorrect = clientService.checkPassword(userId, passwords.getOldPassword());
        if(!isOldPasswordCorrect)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
        Client client = clientService.updatePassword(userId, passwords.getNewPassword());
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

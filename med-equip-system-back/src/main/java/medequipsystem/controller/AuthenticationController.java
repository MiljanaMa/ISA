package medequipsystem.controller;

import javax.servlet.http.HttpServletResponse;

import medequipsystem.domain.Client;
import medequipsystem.dto.auth.ClientRegistrationDTO;
import medequipsystem.service.ClientService;
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
    private PasswordEncoder passwordEncoder;

    @PostMapping("/register")
    public ResponseEntity<Client> addUser(@RequestBody ClientRegistrationDTO clientDto) {

       Client client = this.clientService.create(clientDto);

        if(client == null){
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
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


}

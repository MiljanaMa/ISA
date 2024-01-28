package medequipsystem.controller;

import medequipsystem.domain.Client;
import medequipsystem.domain.Role;
import medequipsystem.domain.SystemAdmin;
import medequipsystem.domain.User;
import medequipsystem.dto.ClientDTO;
import medequipsystem.dto.PasswordChangeDTO;
import medequipsystem.dto.SystemAdminDTO;
import medequipsystem.repository.RoleRepository;
import medequipsystem.service.SystemAdminService;
import medequipsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@CrossOrigin(origins = "http://localhost:4200")
@RequestMapping(value = "api/systemadmins")
public class SystemAdminController {
    @Autowired
    private SystemAdminService systemAdminService;
    @Autowired
    private UserService userService;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<List<SystemAdminDTO>> getAll(){
        List<SystemAdmin> systemAdmins = systemAdminService.getAll();
        List<SystemAdminDTO> systemAdminDTOS = new ArrayList<>();
        for(SystemAdmin sa : systemAdmins){
            systemAdminDTOS.add(new SystemAdminDTO(sa));
        }
        return new ResponseEntity<>(systemAdminDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<SystemAdminDTO> create(@RequestBody SystemAdminDTO systemAdminDTO){
        try {
            SystemAdmin systemAdminToCreate = new SystemAdmin();
            systemAdminToCreate.setMain(systemAdminDTO.isMain());
            systemAdminToCreate.setInitialPasswordChanged(systemAdminDTO.isInitialPasswordChanged());

            User newUser = new User();
            newUser.setEmail(systemAdminDTO.getEmail());
            newUser.setPassword(systemAdminDTO.getPassword());
            newUser.setFirstName(systemAdminDTO.getFirstName());
            newUser.setLastName(systemAdminDTO.getLastName());
            newUser.setCity(systemAdminDTO.getCity());
            newUser.setCountry(systemAdminDTO.getCountry());
            newUser.setPhoneNumber(systemAdminDTO.getPhoneNumber());
            newUser.setEnabled(true);

            LocalDateTime currentDateTime = LocalDateTime.now();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            String formattedTimestamp = currentDateTime.format(formatter);
            Timestamp timestamp = Timestamp.valueOf(formattedTimestamp);
            newUser.setLastPasswordResetDate(timestamp);

            Role role = roleRepository.findByName("ROLE_SYSADMIN");
            newUser.setRole(role);
            systemAdminToCreate.setUser(newUser);
            SystemAdmin savedSystemAdmin = systemAdminService.create(new SystemAdminDTO(systemAdminToCreate));
            SystemAdminDTO savedSystemAdminDTO = new SystemAdminDTO(savedSystemAdmin);

            return new ResponseEntity<>(savedSystemAdminDTO, HttpStatus.CREATED);
        } catch (Exception e) {
            System.out.println("System Admin not created");
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping(value = "/update")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<SystemAdminDTO> update(@RequestBody SystemAdminDTO systemAdminDTO){
        SystemAdmin systemAdmin = systemAdminService.update(systemAdminDTO);
        if(systemAdmin == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @GetMapping(value = "/getbyuserid/{id}")
    public ResponseEntity<SystemAdminDTO> getByUserId(@PathVariable Long id){
        SystemAdmin systemAdmin = systemAdminService.getByUserId(id);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.OK);
    }

    @PreAuthorize("hasRole('SYSADMIN')")
    @PostMapping("/updatePassword")
    public ResponseEntity<SystemAdminDTO> updatePassword(@RequestBody PasswordChangeDTO passwordChangeDTO, Principal user){
        Long userId = userService.getByEmail(user.getName()).getId();
        boolean isOldPasswordCorrect = systemAdminService.checkOldPassword(userId, passwordChangeDTO.getOldPassword());
        if(!isOldPasswordCorrect)
            return new ResponseEntity<>(HttpStatus.BAD_REQUEST);

        SystemAdmin systemAdmin = systemAdminService.updatePassword(userId, passwordChangeDTO.getNewPassword());
        if(systemAdmin == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        return  new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.OK);
    }


}

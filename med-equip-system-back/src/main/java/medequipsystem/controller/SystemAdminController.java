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
            /*System.out.println("\nprovera cuvanja -- 1");
            System.out.println("id " + savedSystemAdmin.getId().toString());
            System.out.println("user id " + savedSystemAdmin.getUser().getId().toString());
            System.out.println("user id " + savedSystemAdmin.getUser().getEmail());
            System.out.println("user city " + savedSystemAdmin.getUser().getCity());
            System.out.println("user role.id " + savedSystemAdmin.getUser().getRole().getId().toString());
            System.out.println("user role.name " + savedSystemAdmin.getUser().getRole().getName());
            System.out.println("main " + savedSystemAdmin.isMain());
            System.out.println("is init pass changed " + savedSystemAdmin.isInitialPasswordChanged());
            */

            SystemAdminDTO savedSystemAdminDTO = new SystemAdminDTO(savedSystemAdmin);
            /*System.out.println("\nprovera cuvanja -- 2");
            System.out.println("id " + savedSystemAdminDTO.getId().toString());
            System.out.println("user id " + savedSystemAdminDTO.getEmail());
            System.out.println("user city " + savedSystemAdminDTO.getCity());
            System.out.println("main " + savedSystemAdminDTO.isMain());
            System.out.println("is init pass changed " + savedSystemAdminDTO.isInitialPasswordChanged());
            */
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

    @GetMapping(value = "/getbyuserid/{id}")
    public ResponseEntity<SystemAdminDTO> getByUserId(@PathVariable Long id){
        SystemAdmin systemAdmin = systemAdminService.getByUserId(id);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdmin), HttpStatus.OK);
    }

    //TODO: update lozinke, metoda iz servisa, sredi front
    @PreAuthorize("hasRole('SYSADMIN')")
    @PostMapping("/updatePassword")
    public ResponseEntity<SystemAdminDTO> updatePassword(@RequestBody PasswordChangeDTO passwordChangeDTO){//String password, String oldPassword, Long userId) {
        //Long userId = userService.getByEmail(user.getName()).getId();
        System.out.println("\n\n ******************** update pass controller 1");
        System.out.println("\n\n *user id: " + passwordChangeDTO.getUserId().toString());
        Long userId = passwordChangeDTO.getUserId();
        String oldPassword = passwordChangeDTO.getOldPassword();
        String newPassword = passwordChangeDTO.getNewPassword();

        SystemAdmin systemAdmin = systemAdminService.getByUserId(userId);
        System.out.println("\n\n ******************** update pass controller");

        if(systemAdmin == null)
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);

        boolean oldPasswordCorrect = systemAdminService.checkOldPassword(userId, oldPassword);
        if(!oldPasswordCorrect){
            return new ResponseEntity<>(HttpStatus.I_AM_A_TEAPOT);
        }
        SystemAdmin systemAdminUpdated = systemAdminService.updatePassword(userId, newPassword);
        systemAdminUpdated.setInitialPasswordChanged(true);
        return new ResponseEntity<>(new SystemAdminDTO(systemAdminUpdated), HttpStatus.OK);
    }

    /*@PreAuthorize("hasRole('SYSADMIN')")
    @GetMapping("/checkPassword")
    public boolean checkPassword(@RequestBody String oldPassword, Principal user){
        Long userId = userService.getByEmail(user.getName()).getId();

        return systemAdminService.checkOldPassword(userId, oldPassword);
    }*/


}

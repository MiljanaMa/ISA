package medequipsystem.controller;

import medequipsystem.domain.Role;
import medequipsystem.domain.SystemAdmin;
import medequipsystem.domain.User;
import medequipsystem.dto.SystemAdminDTO;
import medequipsystem.mapper.SystemAdminDTOMapper;
import medequipsystem.repository.RoleRepository;
import medequipsystem.service.SystemAdminService;
import medequipsystem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
@RequestMapping(value = "api/systemadmins")
public class SystemAdminController {
    @Autowired
    private SystemAdminService systemAdminService;
    @Autowired
    private UserService userService;
    @Autowired
    private SystemAdminDTOMapper systemAdminDTOMapper;
    @Autowired
    private RoleRepository roleRepository;

    @GetMapping(value = "/getAll")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<List<SystemAdminDTO>> getAll(){
        List<SystemAdmin> systemAdmins = systemAdminService.getAll();
        List<SystemAdminDTO> systemAdminDTOS = new ArrayList<>();
        for(SystemAdmin sa : systemAdmins){
            systemAdminDTOS.add(systemAdminDTOMapper.fromSystemAdminToDTO(sa));
        }

        return new ResponseEntity<>(systemAdminDTOS, HttpStatus.OK);
    }

    @PostMapping(value = "/create")
    @PreAuthorize("hasRole('SYSADMIN')")
    public ResponseEntity<SystemAdminDTO> create(@RequestBody SystemAdminDTO systemAdminDTO){
        try {
            // Convert SystemAdminDTO to SystemAdmin
            SystemAdmin systemAdminToCreate = new SystemAdmin();
            systemAdminToCreate.setMain(systemAdminDTO.isMain());
            systemAdminToCreate.setInitialPasswordChanged(systemAdminDTO.isInitialPasswordChanged());

            // Create a new User
            User newUser = new User();
            newUser.setEmail(systemAdminDTO.getEmail());
            newUser.setPassword(systemAdminDTO.getPassword());
            newUser.setFirstName(systemAdminDTO.getFirstName());
            newUser.setLastName(systemAdminDTO.getLastName());
            newUser.setCity(systemAdminDTO.getCity());
            newUser.setCountry(systemAdminDTO.getCountry());
            newUser.setPhoneNumber(systemAdminDTO.getPhoneNumber());
            newUser.setEnabled(true);

            Timestamp currentTimestamp = new Timestamp(System.currentTimeMillis());
            SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ssX");
            String formattedTimestamp = dateFormat.format(currentTimestamp);
            newUser.setLastPasswordResetDate(Timestamp.valueOf(formattedTimestamp));

            Role role = roleRepository.findByName("ROLE_SYSADMIN");
            newUser.setRole(role);

            systemAdminToCreate.setUser(newUser);
            SystemAdmin savedSystemAdmin = systemAdminService.create(systemAdminDTOMapper.fromSystemAdminToDTO(systemAdminToCreate));

            SystemAdminDTO savedSystemAdminDTO = systemAdminDTOMapper.fromSystemAdminToDTO(savedSystemAdmin);

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
        return new ResponseEntity<>(systemAdminDTOMapper.fromSystemAdminToDTO(systemAdmin), HttpStatus.OK);
    }

    //TODO: update lozinke, metoda iz servisa, sredi front
}

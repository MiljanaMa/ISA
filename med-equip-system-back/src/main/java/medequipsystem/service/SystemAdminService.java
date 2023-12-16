package medequipsystem.service;

import medequipsystem.domain.Client;
import medequipsystem.domain.SystemAdmin;
import medequipsystem.domain.User;
import medequipsystem.dto.SystemAdminDTO;
import medequipsystem.repository.RoleRepository;
import medequipsystem.repository.SystemAdminRepository;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SystemAdminService {

    @Autowired
    private SystemAdminRepository systemAdminRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;


    public List<SystemAdmin> getAll(){
        return this.systemAdminRepository.findAll();
    }

    public SystemAdmin getById(Long id){
        Optional<SystemAdmin> systemAdminOptional = this.systemAdminRepository.findById(id);
        return systemAdminOptional.orElse(null);
    }

    public SystemAdmin getByUserId(Long userId){
        SystemAdmin systemAdmin = systemAdminRepository.findByUserId(userId);
        User user = userRepository.getById(userId);
        systemAdmin.setUser(user);
        return systemAdmin;
        //return  this.systemAdminRepository.findByUserId(userId);
    }

    public SystemAdmin create(SystemAdminDTO systemAdminDTO){
        SystemAdmin systemAdmin =  mapDtoToDomain(systemAdminDTO);
        for(User user: userRepository.findAll()) {
            if (systemAdmin.getUser().getEmail().equals(user.getEmail())) {
                return null;
            }
        }
        try {
            return  this.systemAdminRepository.save(systemAdmin);
        }catch (DataIntegrityViolationException e){
            System.out.println(e.getMessage());
            return null;
        }
    }

    public  SystemAdmin update(SystemAdminDTO updatedSystemAdminDTO){
        SystemAdmin systemAdmin = getById(updatedSystemAdminDTO.getId());

        if(systemAdmin == null)
            return  null;

        User user = systemAdmin.getUser();
        user.setFirstName(updatedSystemAdminDTO.getFirstName());
        user.setLastName(updatedSystemAdminDTO.getLastName());
        user.setPhoneNumber(updatedSystemAdminDTO.getPhoneNumber());
        user.setCity(updatedSystemAdminDTO.getCity());
        user.setCountry(updatedSystemAdminDTO.getCountry());

        systemAdmin.setUser(user);
        systemAdmin.setMain(updatedSystemAdminDTO.isMain());
        // password separately
        return  this.systemAdminRepository.save(systemAdmin);
    }

    // call if is_initial_password_changed == false
    public SystemAdmin updatePassword(long userId, String password){
        SystemAdmin systemAdmin = getByUserId(userId);
        if(systemAdmin == null)
            return null;

        User user = systemAdmin.getUser();
        user.setPassword(passwordEncoder.encode(password));
        systemAdmin.setUser(user);

        return this.systemAdminRepository.save(systemAdmin);
    }

    public SystemAdmin mapDtoToDomain(SystemAdminDTO systemAdminDTO){
        User user = new User();
        user.setEmail(systemAdminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(systemAdminDTO.getPassword()));
        user.setFirstName(systemAdminDTO.getFirstName());
        user.setLastName(systemAdminDTO.getLastName());
        user.setCity(systemAdminDTO.getCity());
        user.setCountry(systemAdminDTO.getCountry());
        user.setPhoneNumber(systemAdminDTO.getPhoneNumber());
        user.setEnabled(true);
        user.setRole(roleRepository.findByName("ROLE_SYSADMIN"));

        SystemAdmin systemAdmin = new SystemAdmin();
        systemAdmin.setMain(systemAdminDTO.isMain());
        systemAdmin.setInitialPasswordChanged(systemAdminDTO.isInitialPasswordChanged());
        systemAdmin.setUser(user);
        return systemAdmin;
    }

}

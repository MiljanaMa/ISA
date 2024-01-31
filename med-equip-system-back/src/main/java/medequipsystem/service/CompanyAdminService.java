package medequipsystem.service;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.SystemAdmin;
import medequipsystem.domain.User;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.dto.CompanyAdminRegistrationDTO;
import medequipsystem.dto.SystemAdminDTO;
import medequipsystem.repository.CompanyAdminRepository;
import medequipsystem.repository.RoleRepository;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyAdminService {

    @Autowired
    private CompanyAdminRepository companyAdminRepository;

    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;
    @Autowired
    private UserRepository userRepository;
   // @Autowired
   // private CompanyService companyService;

    public List<CompanyAdmin> getAll() {
        return this.companyAdminRepository.findAll();
    }
    public CompanyAdmin getById(Long id){
        Optional<CompanyAdmin> adminOptional = this.companyAdminRepository.findById(id);
        return adminOptional.orElse(null);
    }

    public CompanyAdmin create(CompanyAdmin companyAdmin){
        return this.companyAdminRepository.save(companyAdmin);
    }

    public CompanyAdmin update(CompanyAdmin updatedCompanyAdmin){
        CompanyAdmin companyAdmin = getById(updatedCompanyAdmin.getId());
        if(companyAdmin == null) return null;

        companyAdmin.setCompany(updatedCompanyAdmin.getCompany());

        return this.companyAdminRepository.save(companyAdmin);
    }

    public List<CompanyAdmin> getFreeAdmins() {
        List<CompanyAdmin> allAdmins = companyAdminRepository.findAll();
        List<CompanyAdmin> freeAdmins = allAdmins.stream()
                .filter(admin -> admin.getCompany() == null || admin.getCompany().getId() == null)
                .collect(Collectors.toList());
        return freeAdmins;
    }

    //TODO: mozda koristiti CompanyAdminRegistrationDTO
    public CompanyAdmin update(CompanyAdminDTO companyAdminDTO){
        CompanyAdmin companyAdmin = companyAdminRepository.getById(companyAdminDTO.getId());

        User user = new User();
        user.setEmail(companyAdminDTO.getUser().getEmail());
        user.setPassword(companyAdminDTO.getUser().getPassword());
        user.setFirstName(companyAdminDTO.getUser().getFirstName());
        user.setLastName(companyAdminDTO.getUser().getLastName());
        user.setCity(companyAdminDTO.getUser().getCity());
        user.setCountry(companyAdminDTO.getUser().getCountry());
        user.setPhoneNumber(companyAdminDTO.getUser().getPhoneNumber());
        companyAdmin.setUser(user);

        companyAdminRepository.save(companyAdmin);

        return companyAdmin;
    }

    public CompanyAdmin get(Long id){
        return this.companyAdminRepository.getById(id);
    }

    public CompanyAdmin getByUserId(Long userId){
        return  this.companyAdminRepository.findByUserId(userId);
    }

    public CompanyAdmin connectWithCompany(Long adminsUserId, Company company){
        CompanyAdmin companyAdmin = getByUserId(adminsUserId);
        System.out.println("get admin by id: "+ companyAdmin.getId().toString());
        companyAdmin.setCompany(company);
        return this.companyAdminRepository.save(companyAdmin);

    }
    public CompanyAdmin getLogged(Principal loggedUser) {
        User user = userRepository.findByEmail(loggedUser.getName());
        if (user == null)
            new Exception("User not found");

        CompanyAdmin admin = this.companyAdminRepository.findByUserId(user.getId());
        if (admin == null)
            new Exception("Admin not found");
        return admin;
    }

   /* public CompanyAdmin mapDtoToDomain(CompanyAdminRegistrationDTO companyAdminDTO){
        User user = new User();
        user.setEmail(companyAdminDTO.getEmail());
        user.setPassword(passwordEncoder.encode(companyAdminDTO.getPassword()));
        user.setFirstName(companyAdminDTO.getFirstName());
        user.setLastName(companyAdminDTO.getLastName());
        user.setCity(companyAdminDTO.getCity());
        user.setCountry(companyAdminDTO.getCountry());
        user.setPhoneNumber(companyAdminDTO.getPhoneNumber());
        user.setEnabled(true);
        user.setRole(roleRepository.findByName("ROLE_COMPADMIN"));
        CompanyAdmin companyAdmin = new CompanyAdmin();
        companyAdmin.setUser(user);
        if (companyAdminDTO.getCompanyId() == 0) {
            companyAdmin.setCompany(null);
        } else {
            Company existingCompany = companyService.getById(companyAdminDTO.getCompanyId());
            companyAdmin.setCompany(existingCompany);
        }
        return companyAdmin;
    }
    */


}

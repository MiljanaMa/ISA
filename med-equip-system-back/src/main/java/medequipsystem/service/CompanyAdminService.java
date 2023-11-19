package medequipsystem.service;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.dto.CompanyAdminDTO;
import medequipsystem.repository.CompanyAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyAdminService {

    @Autowired
    private CompanyAdminRepository companyAdminRepository;

    public List<CompanyAdmin> getAll() {
        return this.companyAdminRepository.findAll();
    }

    public CompanyAdmin create(CompanyAdmin companyAdmin){
        return this.companyAdminRepository.save(companyAdmin);
    }

    public CompanyAdmin update(CompanyAdminDTO companyAdminDTO){
        CompanyAdmin companyAdmin = companyAdminRepository.getById(companyAdminDTO.getId());
        companyAdmin.setEmail(companyAdminDTO.getEmail());
        companyAdmin.setPassword(companyAdminDTO.getPassword());
        companyAdmin.setFirstName(companyAdminDTO.getFirstName());
        companyAdmin.setLastName(companyAdminDTO.getLastName());
        companyAdmin.setCity(companyAdminDTO.getCity());
        companyAdmin.setCountry(companyAdminDTO.getCountry());
        companyAdmin.setPhoneNumber(companyAdminDTO.getPhoneNumber());


        companyAdminRepository.save(companyAdmin);

        return companyAdmin;
    }
    public CompanyAdmin get(Long id){
        return this.companyAdminRepository.getById(id);
    }
}

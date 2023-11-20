package medequipsystem.service;

import medequipsystem.domain.CompanyAdmin;
import medequipsystem.repository.CompanyAdminRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class CompanyAdminService {

    @Autowired
    private CompanyAdminRepository companyAdminRepository;

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
                .filter(admin -> admin.getCompany() == null  || admin.getCompany().getId() == null)
                .collect(Collectors.toList());
        return freeAdmins;
    }
}

package medequipsystem.service;

import medequipsystem.domain.CompanyAdmin;
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
}

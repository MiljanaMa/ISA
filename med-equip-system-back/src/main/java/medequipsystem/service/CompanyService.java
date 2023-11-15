package medequipsystem.service;

import medequipsystem.domain.Company;
import medequipsystem.repository.CompanyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CompanyService {
    @Autowired
    private CompanyRepository companyRepository;

    public List<Company> getAll() {
        return this.companyRepository.findAll();
    }
}

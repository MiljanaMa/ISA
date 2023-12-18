package medequipsystem.service;

import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyAdmin;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.repository.CompanyEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CompanyEquipmentService {

    @Autowired
    private CompanyEquipmentRepository companyEquipmentRepository;

    public List<CompanyEquipment> getAll() {
        return this.companyEquipmentRepository.findAll();
    }

    public CompanyEquipment create(CompanyEquipment companyEquipment){
        return this.companyEquipmentRepository.save(companyEquipment);
    }

    public void update(CompanyEquipment companyEquipment){
        this.companyEquipmentRepository.save(companyEquipment);
    }

    public CompanyEquipment getById(long id){
        return this.companyEquipmentRepository.getById(id);
    }

}

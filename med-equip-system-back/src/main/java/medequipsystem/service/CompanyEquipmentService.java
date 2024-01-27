package medequipsystem.service;

import medequipsystem.domain.*;
import medequipsystem.repository.CompanyEquipmentRepository;
import medequipsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.Set;

@Service
public class CompanyEquipmentService {

    @Autowired
    private CompanyEquipmentRepository companyEquipmentRepository;

    @Autowired
    private ReservationService reservationService;


    public List<CompanyEquipment> getAll() {
        return this.companyEquipmentRepository.findAll();
    }

    public CompanyEquipment create(CompanyEquipment companyEquipment){
        return this.companyEquipmentRepository.save(companyEquipment);
    }

    public Optional<CompanyEquipment> getByName(String name){
        return this.companyEquipmentRepository.findFirstByName(name);
    }

    public void update(CompanyEquipment companyEquipment){
        this.companyEquipmentRepository.save(companyEquipment);
    }

    public CompanyEquipment getById(long id){
        return this.companyEquipmentRepository.getById(id);
    }

    public boolean IsUnpicked(long id){
        Set<Reservation> reservations = reservationService.getReservationsInProgress();
        for(Reservation r: reservations){

            var items = r.getReservationItems();
            for(ReservationItem i: items){
                if(i.getEquipment().getId() == id){
                    return true;
                }
            }
        }
        return false;
    }

}

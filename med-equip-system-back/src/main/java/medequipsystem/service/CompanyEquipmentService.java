package medequipsystem.service;

import medequipsystem.domain.*;
import medequipsystem.dto.CompanyEquipmentProfileDTO;
import medequipsystem.dto.ReservationItemDTO;
import medequipsystem.repository.CompanyEquipmentRepository;
import medequipsystem.repository.ReservationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashSet;
import java.util.List;
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

    @Transactional(readOnly = false)
    public void update(CompanyEquipment companyEquipment){
        this.companyEquipmentRepository.save(companyEquipment);
    }

    public CompanyEquipment getById(long id){
        return this.companyEquipmentRepository.getById(id);
    }
    //Transactional, provjerava dostupnost opreme, optimistic-vrv bolje/pessimistic read/write
    public boolean checkEquipmentAvailability(Set<ReservationItem> items){
        CompanyEquipment ce;
        for(ReservationItem i: items){
            //komunikacija sa bazom
            ce = this.companyEquipmentRepository.getById(i.getId());
            if(ce.getCount()-ce.getReservedCount() < i.getCount()){
                return false;
            }
        }
        return false;
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
    public Set<ReservationItem> getEquipmentForReservation(Set<ReservationItem> reservationItems) {
        //dok ja provjeravam neko vrslja po bazi, ali version rijesi taj problem
        CompanyEquipment ce;
        Set<ReservationItem> updatedItems = new HashSet<>();
        for(ReservationItem ri: reservationItems){
            //exception not handeled
            ce = companyEquipmentRepository.getReferenceById(ri.getEquipment().getId());
            if(ri.getCount() > (ce.getCount() - ce.getReservedCount()))
                return null;
            ce.setReservedCount(ce.getReservedCount() + ri.getCount());
            updatedItems.add(new ReservationItem(ri.getId(), ri.getCount(), ce, ri.getCount()*ce.getPrice()));
        }
        return updatedItems;
    }

}

package medequipsystem.service;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.Reservation;
import medequipsystem.domain.ReservationItem;
import medequipsystem.repository.CompanyEquipmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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

    public CompanyEquipment create(CompanyEquipment companyEquipment) {
        return this.companyEquipmentRepository.save(companyEquipment);
    }

    @Transactional(readOnly = false)
    public void update(CompanyEquipment updatedEquipment) throws Exception {
        try {
            CompanyEquipment equipment = companyEquipmentRepository.getById(updatedEquipment.getId());
            if (equipment == null) {
                throw new Exception("Equipment is deleted");
            }
            equipment.setCount(updatedEquipment.getCount());
            equipment.setDescription(updatedEquipment.getDescription());
            equipment.setName(updatedEquipment.getName());
            equipment.setPrice(updatedEquipment.getPrice());
            equipment.setType(updatedEquipment.getType());
            this.companyEquipmentRepository.save(equipment);

        } catch (Exception e) {
            throw new Exception("Equipment is changed try again.");
        }
    }

    public CompanyEquipment getById(long id) {
        return this.companyEquipmentRepository.getById(id);
    }

    public boolean IsUnpicked(long id) {
        Set<Reservation> reservations = reservationService.getReservationsInProgress();
        for (Reservation r : reservations) {

            var items = r.getReservationItems();
            for (ReservationItem i : items) {
                if (i.getEquipment().getId() == id) {
                    return true;
                }
            }
        }
        return false;
    }

}

package medequipsystem.service;

import medequipsystem.domain.Appointment;
import medequipsystem.repository.AppointmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Set;

@Service
public class AppointmentService {

    @Autowired
    private AppointmentRepository appointmentRepository;

    public Set<Appointment> getByCompany(Long id){
        return appointmentRepository.getByCompanyId(id);
    }
}

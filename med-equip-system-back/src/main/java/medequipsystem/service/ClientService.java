package medequipsystem.service;

import medequipsystem.domain.Client;
import medequipsystem.domain.User;
import medequipsystem.dto.ClientDTO;
import medequipsystem.dto.auth.ClientRegistrationDTO;
import medequipsystem.repository.ClientRepository;
import medequipsystem.repository.RoleRepository;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Client> getAll(){
        return this.clientRepository.findAll();
    }

    public Client getById(Long id){
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    public Client getByUserId(Long userId){
        return this.clientRepository.findByUserId(userId);
    }

    public Client create(ClientRegistrationDTO clientRegistrationDTO){

        Client client = mapClientDtoToDomain(clientRegistrationDTO);

        for(User u: userRepository.findAll()) {
            if(client.getUser().getEmail().equals(u.getEmail())){
                return null;  //already exists
            }
        }
        try {
            return this.clientRepository.save(client);
        } catch (DataIntegrityViolationException e) {
            // Handle the exception caused by a duplicate key violation
            System.out.println(e.getMessage());
            return null;
        }
    }

    public Client update(ClientDTO updatedClientDto){

        Client client = getById(updatedClientDto.getId());
        if(client == null) return null;
        User user = client.getUser();

        user.setFirstName(updatedClientDto.getFirstName());
        user.setLastName(updatedClientDto.getLastName());
        user.setCity(updatedClientDto.getCity());
        user.setCountry(updatedClientDto.getCountry());
        user.setPhoneNumber(updatedClientDto.getPhoneNumber());

        client.setUser(user);
        client.setJobTitle(updatedClientDto.getJobTitle());
        client.setHospitalInfo(updatedClientDto.getHospitalInfo());

        return this.clientRepository.save(client);
    }

    public Client updatePassword(long userId, String password){
        Client client = getByUserId(userId);
        if(client == null) return null;
        User user = client.getUser();
        user.setPassword(passwordEncoder.encode(password));
        client.setUser(user);
        return this.clientRepository.save(client);
    }
    public boolean checkPassword(long userId, String password){
        Client client = getByUserId(userId);
        if(client == null)
            return false;

        boolean isPasswordMatch = passwordEncoder.matches(password, client.getUser().getPassword());
        if(!isPasswordMatch)
            return false;

        return true;
    }

    public Client mapClientDtoToDomain(ClientRegistrationDTO clientDTO) {
        User user = new User();
        user.setEmail(clientDTO.getEmail());
        user.setPassword(passwordEncoder.encode(clientDTO.getPassword()));
        user.setFirstName(clientDTO.getFirstName());
        user.setLastName(clientDTO.getLastName());
        user.setCity(clientDTO.getCity());
        user.setCountry(clientDTO.getCountry());
        user.setPhoneNumber(clientDTO.getPhoneNumber());
        user.setEnabled(false);
        user.setRole(roleRepository.findByName("ROLE_CLIENT"));

        Client client = new Client();
        client.setJobTitle(clientDTO.getJobTitle());
        client.setHospitalInfo(clientDTO.getHospitalInfo());
        client.setPenaltyPoints(0);
        client.setPoints(0);
        client.setUser(user);
        return client;
    }
    public void updatePenaltyPoints() {
        Calendar cal = Calendar.getInstance();
        int dayOfMonth = cal.get(Calendar.DAY_OF_MONTH);
        int month = cal.get(Calendar.MONTH);
        if(dayOfMonth != 1)
            return;
        for(Client c: getAll()){
            if(c.getVersionPenalty() == month)
                return;
            c.setPenaltyPoints(0);
            clientRepository.save(c);
        }
    }

    public void confirmEmail(Client client){
       User user = client.getUser();
       user.setEnabled(true);
       userRepository.save(user);
    }

    public void penalize(Long clientId, LocalDate appointmentDate, LocalTime appointmentTime){
        Client client = getById(clientId);
        int penaltyPoints = calculatePenaltyPoints(appointmentDate, appointmentTime);
        client.setPenaltyPoints(client.getPenaltyPoints() + penaltyPoints);
        clientRepository.save(client);
    }

    public int calculatePenaltyPoints(LocalDate appointmentDate, LocalTime appointmentTime){
        boolean isAppointmentNextDay = appointmentDate.equals(LocalDate.now().plusDays(1));
        boolean isAppointmentTimePassed = appointmentTime.isBefore(LocalTime.now());
        boolean isAppointmentToday = appointmentDate.equals(LocalDate.now());

        if((isAppointmentNextDay && isAppointmentTimePassed) || isAppointmentToday){
            return 2;
        }
        return 1;
    }


}

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
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import java.security.Principal;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;
import java.util.Set;

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

    public List<Client> getAll() {
        return this.clientRepository.findAll();
    }

    public Client getById(Long id) {
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        return clientOptional.orElse(null);
    }

    public Client getByUserId(Long userId) {
        return this.clientRepository.findByUserId(userId);
    }

    public boolean hasPermissionToReserve(Long userId) {
        Client client = this.clientRepository.findByUserId(userId);
        return client.getPenaltyPoints() < 3;
    }

    public Set<Client> getByAdminId(Long id){
        return this.clientRepository.findClientsByAdminId(id);
    }

    public Client getLogged(Principal loggedUser) {
        User user = userRepository.findByEmail(loggedUser.getName());
        if (user == null)
            new Exception("User not found");

        Client client = this.clientRepository.findByUserId(user.getId());
        if (client == null)
            new Exception("Client not found");
        else if(client.getPenaltyPoints() > 3)
            new Exception("You are not allowed, because of penalty points");
        return client;
    }

    public Client create(ClientRegistrationDTO clientRegistrationDTO) {

        Client client = mapClientDtoToDomain(clientRegistrationDTO);

        for (User u : userRepository.findAll()) {
            if (client.getUser().getEmail().equals(u.getEmail())) {
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

    public Client update(ClientDTO updatedClientDto) {

        Client client = getById(updatedClientDto.getId());
        if (client == null) return null;
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

    public Client updatePassword(long userId, String password) {
        Client client = getByUserId(userId);
        if (client == null) return null;
        User user = client.getUser();
        user.setPassword(passwordEncoder.encode(password));
        client.setUser(user);
        return this.clientRepository.save(client);
    }

    public boolean checkPassword(long userId, String password) {
        Client client = getByUserId(userId);
        if (client == null)
            return false;

        boolean isPasswordMatch = passwordEncoder.matches(password, client.getUser().getPassword());
        if (!isPasswordMatch)
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

    @Scheduled(cron = "0 0 0 1 * ?")
    public void updatePenaltyPoints() {
        for (Client c : getAll()) {
            c.setPenaltyPoints(0);
            clientRepository.save(c);
        }
    }

    public void confirmEmail(Client client) {
        User user = client.getUser();
        user.setEnabled(true);
        userRepository.save(user);
    }


    public void penalizeCancellation(Long clientId, LocalDate appointmentDate, LocalTime appointmentTime){
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

    public void penalizeExpiration(Client client, int penaltyPoints){
        client.setPenaltyPoints(client.getPenaltyPoints() + 2);
        this.clientRepository.save(client);
    }

}

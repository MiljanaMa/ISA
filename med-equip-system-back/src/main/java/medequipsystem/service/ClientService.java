package medequipsystem.service;

import medequipsystem.domain.Client;
import medequipsystem.repository.ClientRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientService {

    @Autowired
    private ClientRepository clientRepository;

    public List<Client> getAll(){
        return this.clientRepository.findAll();
    }

    public Client getById(Long id){
        Optional<Client> clientOptional = this.clientRepository.findById(id);
        return clientOptional.orElse(null);
    }
}

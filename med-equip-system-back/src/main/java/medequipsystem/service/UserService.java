package medequipsystem.service;

import medequipsystem.domain.User;
import medequipsystem.repository.LoyaltyProgramRepository;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    public List<User> getAll(){
        return this.userRepository.findAll();
    }

    public User getById(Long id){
        Optional<User> userOptional = this.userRepository.findById(id);
        return userOptional.orElse(null);
    }

    public User getByEmail(String email){
        return this.userRepository.findByEmail(email);
    }


}
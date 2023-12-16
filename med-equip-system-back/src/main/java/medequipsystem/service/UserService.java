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
    /*



    public User updatePassword(long userId, String password){
        User user = getById(userId);
        if(user == null) return null;
        user.setPassword(password);
        return this.userRepository.save(user);
    }

    public User setEmailAsConfirmed(User user){
        user.setEmailConfirmed(true);
        return this.userRepository.save(user);
    }
*/
}
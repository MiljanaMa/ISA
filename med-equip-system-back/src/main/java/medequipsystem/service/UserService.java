package medequipsystem.service;

import medequipsystem.domain.User;
import medequipsystem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
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

    public User create(User user){
        for(User u: getAll()) {
            if(user.getEmail().equals(u.getEmail())){
                return null;
            }
        }
        return this.userRepository.save(user);
    }
    public User update(User user){
        return this.userRepository.save(user);
    }

}
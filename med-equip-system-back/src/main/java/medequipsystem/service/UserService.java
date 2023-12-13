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
    /*

    public User create(User user){
        for(User u: getAll()) {
            if(user.getEmail().equals(u.getEmail())){
                return null;
            }
        }
        try {
            return this.userRepository.save(user);
        } catch (DataIntegrityViolationException e) {
            // Handle the exception caused by a duplicate key violation
            // You may want to log the error or take appropriate action
            System.out.println(e.getMessage());
            return null;
        }
        //return this.userRepository.save(user);
    }
    public User update(User updatedUser){
        User user = getById(updatedUser.getId());

        if(user == null) return null;

        user.setFirstName(updatedUser.getFirstName());
        user.setLastName(updatedUser.getLastName());
        user.setCity(updatedUser.getCity());
        user.setCountry(updatedUser.getCountry());
        user.setPhoneNumber(updatedUser.getPhoneNumber());
        user.setJobTitle(updatedUser.getJobTitle());
        user.setHospitalInfo(updatedUser.getHospitalInfo());

        return this.userRepository.save(user);
    }
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
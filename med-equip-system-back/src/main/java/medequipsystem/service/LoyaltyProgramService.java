package medequipsystem.service;

import medequipsystem.domain.LoyaltyProgram;
import medequipsystem.domain.User;
import medequipsystem.repository.LoyaltyProgramRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoyaltyProgramService {
    @Autowired
    private LoyaltyProgramRepository loyaltyRepository;
    public LoyaltyProgram getUserLoyaltyType(int points, int penaltyPoints){

        for (LoyaltyProgram lp: this.loyaltyRepository.findAll(Sort.by(Sort.Direction.DESC, "minPoints")))
        {
            if(points >= lp.getMinPoints() && penaltyPoints <= lp.getMaxPenaltyPoints())
                return lp;
        }
        return null;
    }

}

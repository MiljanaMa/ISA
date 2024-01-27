package medequipsystem.service;

import medequipsystem.domain.Contract;
import medequipsystem.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class ContractService {

    @Autowired
    ContractRepository contractRepository;

    public Contract create(Contract contract){ return this.contractRepository.save(contract); }

}

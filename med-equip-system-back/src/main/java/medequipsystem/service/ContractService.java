package medequipsystem.service;

import medequipsystem.domain.Contract;
import medequipsystem.domain.enums.ContractStatus;
import medequipsystem.rabbitmq.ContractSenderService;
import medequipsystem.repository.ContractRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Set;

@Service
public class ContractService {

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractSenderService senderService;

    public Contract create(Contract contract){ return this.contractRepository.save(contract); }

    public Set<Contract> getByCompany(Long id){ return this.contractRepository.getByCompanyId(id); }
    public Contract cancelContract(Long id){
        Contract contract = contractRepository.findById(id).get();
        contract.setStatus(ContractStatus.CANCELLED);
        Contract cancelledContract = this.contractRepository.save(contract);
        senderService.cancel(cancelledContract.getId());
        return cancelledContract;
    }
    @Scheduled(cron = "0 0 12 * * ?")
    public void startDelivery(){
        for(Contract c: contractRepository.findAll()){
            if(c.getDate().equals(LocalDate.now())){
                c.setStatus(ContractStatus.ACTIVE);
                contractRepository.save(c);
                senderService.start(c.getId());
            }

        }
    }
    @Scheduled(cron = "40 0 12 * * ?")
    public void endDelivery(){
        for(Contract c: contractRepository.findAll()){
            if(c.getDate().equals(LocalDate.now())){
                c.setStatus(ContractStatus.FINISHED);
                contractRepository.save(c);
                senderService.finish(c.getId());
            }

        }
    }

}

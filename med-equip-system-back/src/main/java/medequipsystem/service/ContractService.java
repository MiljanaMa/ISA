package medequipsystem.service;

import medequipsystem.domain.CompanyEquipment;
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
    @Scheduled(cron = "0 14 13 * * ?")
    public void startDelivery(){
        for(Contract c: contractRepository.findAll()){
            if(shouldStartDelivery(c)){
                c.setStatus(ContractStatus.ACTIVE);
                contractRepository.save(c);
                senderService.start(c.getId());
            }

        }
    }
    @Scheduled(cron = "40 14 13 * * ?")
    public void endDelivery(){
        for(Contract c: contractRepository.findAll()){
            if(c.getStatus() == ContractStatus.ACTIVE){
                c.setStatus(ContractStatus.INACTIVE);
                contractRepository.save(c);
                senderService.finish(c.getId());
            }else if(shouldChangeCancelled(c)){
                c.setStatus(ContractStatus.INACTIVE);
                contractRepository.save(c);
            }

        }
    }
    @Scheduled(cron = "0 0 11 * * ?")
    public void cancelDelivery(){
        for(Contract c: contractRepository.findAll()){
            if(shouldCancelDelivery(c)){
                c.setStatus(ContractStatus.CANCELLED);
                contractRepository.save(c);
                senderService.cancel(c.getId());
            }

        }
    }
    private boolean shouldCancelDelivery(Contract contract){
        boolean isDayValid = contract.getDate() == LocalDate.now().plusDays(3).getDayOfMonth();
        if(!isDayValid)
            return false;
        CompanyEquipment equipment = contract.getCompanyEquipment();
        boolean notEnoughEquipment = (equipment.getCount() - equipment.getReservedCount()) - contract.getTotal() < 0;
        if(contract.getStatus() == ContractStatus.INACTIVE && notEnoughEquipment)
            return true;
        return false;
    }
    private boolean shouldStartDelivery(Contract contract){
        boolean isDateValid = contract.getDate() == LocalDate.now().getDayOfMonth();
        if(isDateValid && contract.getStatus() == ContractStatus.INACTIVE)
            return true;
        return false;
    }
    private boolean shouldChangeCancelled(Contract contract){
        boolean isDateValid = contract.getDate() == LocalDate.now().getDayOfMonth();
        if(isDateValid && contract.getStatus() == ContractStatus.CANCELLED)
            return true;
        return false;
    }

}

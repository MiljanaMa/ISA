package medequipsystem.service;

import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.Contract;
import medequipsystem.domain.enums.ContractStatus;
import medequipsystem.rabbitmq.ContractSenderService;
import medequipsystem.repository.ContractRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Optional;
import java.util.Set;

@Service
public class ContractService {

    @Autowired
    ContractRepository contractRepository;
    @Autowired
    ContractSenderService senderService;

    private final Logger LOG = LoggerFactory.getLogger(CompanyService.class);


    public Contract create(Contract contract) {
        return this.contractRepository.save(contract);
    }

    public Set<Contract> getByCompany(Long id) {
        return this.contractRepository.getByCompanyId(id);
    }

    public Optional<Contract> getByHospitalAndStatus(String hospital,
                                                     ContractStatus status) {
        return this.contractRepository.findFirstByHospitalAndStatus(hospital, status);
    }

    @Cacheable("contract")
    public Contract getById(Long id){
        LOG.info("Contract with id: " + id + " successfully cached.");
        return this.contractRepository.findById(id).orElse(null);
    }

    @CacheEvict(cacheNames = {"contract"}, allEntries = true)
    public void removeAllFromCache(){
        LOG.info("Contracts removed from cache.");
    }

    @CacheEvict(cacheNames = "contract", key = "#contractId")
    public void removeFromCache(Long contractId){LOG.info("Contract with ID {} remove from cache.", contractId);}

    @CachePut(cacheNames = "contract", key = "#id")
    public Contract cancelContract(Long id) {
        Contract contract = contractRepository.findById(id).get();
        contract.setStatus(ContractStatus.CANCELLED);
        Contract cancelledContract = this.contractRepository.save(contract);
        senderService.cancel(cancelledContract.getId());
        return cancelledContract;
    }

    @CachePut(cacheNames = "contract", key = "#id")
    public Contract invalidateContract(Long id) {
        Contract contract = contractRepository.findById(id).get();
        contract.setStatus(ContractStatus.INVALID);
        Contract invalidContract = this.contractRepository.save(contract);
        senderService.invalidate(invalidContract.getId());
        return invalidContract;
    }

    @CachePut(cacheNames = "contract", key = "#contract.id")
    public Contract updateContractCache(Contract contract){
        return contractRepository.save(contract);

    }

    @Scheduled(cron = "0 25 0 * * ?")
    public void startDelivery() {
        for (Contract c : contractRepository.getByDateAndStatus(LocalDate.now().getDayOfMonth(), ContractStatus.INACTIVE)) {
            c.setStatus(ContractStatus.ACTIVE);
            updateContractCache(c);
            senderService.start(c.getId());
        }
    }

    @Scheduled(cron = "40 25 0 * * ?")
    public void endDelivery() {
        for (Contract c : contractRepository.getByDateAndStatus(LocalDate.now().getDayOfMonth(), ContractStatus.ACTIVE)) {
            c.setStatus(ContractStatus.INACTIVE);
            updateContractCache(c);
            senderService.finish(c.getId());

        }
        for (Contract c : contractRepository.getByDateAndStatus(LocalDate.now().getDayOfMonth(), ContractStatus.CANCELLED)) {
            c.setStatus(ContractStatus.INACTIVE);
            updateContractCache(c);

        }
    }

    @Scheduled(cron = "0 0 11 * * ?")
    public void cancelDelivery() {
        for (Contract c : contractRepository.findAll()) {
            if (shouldCancelDelivery(c)) {
                c.setStatus(ContractStatus.CANCELLED);
                updateContractCache(c);
                senderService.cancel(c.getId());
            }

        }
    }

    private boolean shouldCancelDelivery(Contract contract) {
        boolean isDayValid = contract.getDate() == LocalDate.now().plusDays(3).getDayOfMonth();
        if (!isDayValid)
            return false;
        CompanyEquipment equipment = contract.getCompanyEquipment();
        boolean notEnoughEquipment = (equipment.getCount() - equipment.getReservedCount()) - contract.getTotal() < 0;
        if (contract.getStatus() == ContractStatus.INACTIVE && notEnoughEquipment)
            return true;
        return false;
    }
}

package medequipsystem.rabbitmq;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import medequipsystem.domain.Company;
import medequipsystem.domain.CompanyEquipment;
import medequipsystem.domain.Contract;
import medequipsystem.dto.ContractDTO;
import medequipsystem.mapper.MapperUtils.DtoUtils;
import medequipsystem.service.CompanyEquipmentService;
import medequipsystem.service.CompanyService;
import medequipsystem.service.ContractService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.amqp.rabbit.annotation.RabbitListener;

import java.util.Optional;

@Service
public class ContractReceiverService {

    @Autowired
    CompanyEquipmentService equipmentService;

    @Autowired
    CompanyService companyService;

    @Autowired
    ContractService contractService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @RabbitListener(queues = "contract")
    public void receive(Message message) throws JsonProcessingException {
        System.out.println("[*] Received\n" + new String(message.getBody()));

        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        ContractDTO contractDTO = mapper.readValue(new String(message.getBody()),ContractDTO.class);

        Contract contract = (Contract) new DtoUtils().convertToEntity(new Contract(), contractDTO);

        Optional<CompanyEquipment> equipment = equipmentService.getByName(contractDTO.getEquipmentName());
        Optional<Company> company = companyService.getByName(contractDTO.getCompanyName());


        String replyTo = message.getMessageProperties().getReplyTo();
        String correlationId = message.getMessageProperties().getCorrelationId();

        String response;

        if(equipment.isEmpty() || company.isEmpty()){
            response = "Couldn't find company or equipment";
            System.out.println("[x] Sending message: " + response);
        }
        else{
            CompanyEquipment equipmentValue = equipment.get();

            if((equipmentValue.getCount() - equipmentValue.getReservedCount()) >= contract.getTotal()){
                contract.setCompanyEquipment(equipmentValue);
                contract.setCompany(company.get());

                Contract savedContract = contractService.create(contract);

                response = Long.toString(savedContract.getId());


                if(contractDTO.getId() == null) {
                    System.out.println("[x] Sending id: " + response);


                }
            }else{
                response = "Not enough equipment";
                System.out.println("[x] Sending message: " + response);
            }
        }



        rabbitTemplate.convertAndSend("", replyTo, response, m -> {
            m.getMessageProperties().setCorrelationId(correlationId);
            return m;
        });


    }





}

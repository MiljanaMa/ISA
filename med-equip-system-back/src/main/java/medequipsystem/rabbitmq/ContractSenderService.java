package medequipsystem.rabbitmq;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

@Service
public class ContractSenderService {

    @Autowired
    private RabbitTemplate template;

    @Autowired
    private DirectExchange exchange;
    public void start(Long contractId){
            System.out.println(" [x] Sending message START for contract id: " + Long.toString(contractId));
            template.convertAndSend(exchange.getName(), "start", Long.toString(contractId));
    }
    public void finish(Long contractId){
            System.out.println(" [x] Sending message FINISH for contract id: " + contractId);
            template.convertAndSend(exchange.getName(), "finish", Long.toString(contractId));
    }
    public void cancel(Long contractId){
            System.out.println(" [x] Sending message CANCEL for contract id: " + contractId);
            template.convertAndSend(exchange.getName(), "cancel", Long.toString(contractId));
    }
    public void invalidate(Long contractId){
        System.out.println(" [x] Sending message INVALID for contract id: " + contractId);
        template.convertAndSend(exchange.getName(), "invalid", Long.toString(contractId));
    }

}

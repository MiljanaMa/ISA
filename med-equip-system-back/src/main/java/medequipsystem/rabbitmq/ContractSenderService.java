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

    private int timesSent = 0;

    @Scheduled(fixedDelay = 20000, initialDelay = 20000)
    public void send(){
        if(timesSent < 2) {
            System.out.println(" [x] Sending message CANCEL for contract id: " + 1);
            template.convertAndSend(exchange.getName(), "cancel", "1");

            System.out.println(" [x] Sending message FINISH for contract id: " + 2);
            template.convertAndSend(exchange.getName(), "finish", "2");

            System.out.println(" [x] Sending message START for contract id: " + 3);
            template.convertAndSend(exchange.getName(), "start", "3");

            ++timesSent;
        }
    }

}

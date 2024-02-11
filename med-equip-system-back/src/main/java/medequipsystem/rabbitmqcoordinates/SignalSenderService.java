package medequipsystem.rabbitmqcoordinates;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class SignalSenderService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    public void startSendingLocations() {
        System.out.println("[*] Sending signal trigger AAA: ");
        rabbitTemplate.convertAndSend("startsending", "Start sending coordinates");
    }
}

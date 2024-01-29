package medequipsystem.rabbitmqcoordinates;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

@Service
public class CoordinateRecieverService {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Autowired
    private SimpMessagingTemplate simpMessagingTemplate;

    @RabbitListener(queues = "coordinates")
    public void receive(String message){
        simpMessagingTemplate.convertAndSend("/socket-publisher", message);
        System.out.println("[*] Received: " + message);
    }

}



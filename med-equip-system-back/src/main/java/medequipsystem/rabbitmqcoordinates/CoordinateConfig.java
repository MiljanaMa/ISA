package medequipsystem.rabbitmqcoordinates;


import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoordinateConfig {
    @Bean
    public Queue coordinateQueue(){
        return new Queue("coordinates");
    }

}

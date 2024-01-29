package medequipsystem.rabbitmqcoordinates;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoordinateConfig {
    @Bean
    public Queue coordinateQueue(){
        return new Queue("coordinates");
    }

    @Bean
    public Queue startSendingQueue() {
        return new Queue("startsending");
    }

}

package medequipsystem.rabbitmqcoordinates;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class CoordinateConfig {
    @Bean
    public DirectExchange coordinatesExchange() {
        return new DirectExchange("coordinates_exchange");
    }

    /*@Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        RabbitTemplate template = new RabbitTemplate(connectionFactory);
        template.setExchange("coordinates_exchange");
        // You can customize other RabbitTemplate settings if needed
        return template;
    }*/
}

package medequipsystem.rabbitmq;

import org.springframework.amqp.core.DirectExchange;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class ContractConfig {
    @Bean
    public DirectExchange directExchange(){

        return new DirectExchange("direct_updates");
    }
}

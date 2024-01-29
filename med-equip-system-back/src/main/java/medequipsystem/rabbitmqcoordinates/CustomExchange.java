package medequipsystem.rabbitmqcoordinates;

import org.springframework.amqp.core.Exchange;

public interface CustomExchange {
    Exchange getExchange();

}

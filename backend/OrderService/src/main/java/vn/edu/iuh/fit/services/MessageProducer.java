package vn.edu.iuh.fit.services;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Service;
import vn.edu.iuh.fit.configs.RabbitMQConfig;
import vn.edu.iuh.fit.dtos.OrderCreatedEvent;
import vn.edu.iuh.fit.dtos.ProductQuantityRequest;
import vn.edu.iuh.fit.models.OrderItem;

import java.util.List;


@Service
public class MessageProducer {
    //Inject RabbitTemplate for sending messages to RabbitMQ
    private final RabbitTemplate rabbitTemplate;

    public MessageProducer(RabbitTemplate rabbitTemplate) {
        this.rabbitTemplate = rabbitTemplate;
    }
    //send Message to user_queue
    public void sendProductQuantity(OrderCreatedEvent orderCreatedEvent) {
        System.out.println("orderCreatedEvent: " + orderCreatedEvent);
        rabbitTemplate.convertAndSend(RabbitMQConfig.QUEUE_NAME, orderCreatedEvent);
    }
}

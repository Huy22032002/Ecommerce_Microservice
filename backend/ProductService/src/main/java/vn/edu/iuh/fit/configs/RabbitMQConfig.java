package vn.edu.iuh.fit.configs;

import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import vn.edu.iuh.fit.dtos.OrderCreatedEvent;
import vn.edu.iuh.fit.services.ProductService;

@Configuration
@EnableRabbit
public class RabbitMQConfig {

    @Autowired
    private ProductService productService;

    private static final String QUEUE_NAME = "order_queue";

    @Bean
    public Queue queue() {
        return new Queue(QUEUE_NAME, false);
    }

    // Convert JSON -> Object
    @Bean
    public Jackson2JsonMessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    // Gán converter vào factory để dùng cho @RabbitListener
    @Bean
    public SimpleRabbitListenerContainerFactory rabbitListenerContainerFactory(
            ConnectionFactory connectionFactory,
            Jackson2JsonMessageConverter messageConverter) {

        SimpleRabbitListenerContainerFactory factory = new SimpleRabbitListenerContainerFactory();
        factory.setConnectionFactory(connectionFactory);
        factory.setMessageConverter(messageConverter);
        return factory;
    }

    @RabbitListener(queues = QUEUE_NAME)
    public void listen(OrderCreatedEvent event) {
        System.out.println("Received from Order Service: " + event);
        productService.handleQueue(event);
    }
}

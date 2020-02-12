package fr.tiw1.banque.client;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

@Component
public class RabbitmqProducer {
    private Logger LOGGER = LoggerFactory.getLogger(RabbitmqProducer.class);

    private Queue queue;
    private RabbitTemplate rabbitTemplate;

    public RabbitmqProducer(Queue queue, RabbitTemplate rabbitTemplate) {
        this.queue = queue;
        this.rabbitTemplate = rabbitTemplate;
    }

    public void send(String activationNumber) {
        System.out.println(queue.getName());
        this.rabbitTemplate.convertAndSend(queue.getName(), activationNumber);
        LOGGER.debug(" Activation number sent '" + activationNumber + "'");
    }

}

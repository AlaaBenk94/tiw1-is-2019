package fr.tiw1.banque.client.config;

import org.springframework.amqp.core.Queue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitConfiguration {

    private RabbitProperties rabbitProperties;

    public RabbitConfiguration(RabbitProperties rabbitProperties) {
        this.rabbitProperties = rabbitProperties;
    }

    @Bean
    public Queue myQueue() {
        System.out.println("*****************************************************************************");
        System.out.println("this is : " + rabbitProperties.getQueueName());
        System.out.println("*****************************************************************************");
        Queue queue = new Queue(rabbitProperties.getQueueName());
        return queue;
    }

}

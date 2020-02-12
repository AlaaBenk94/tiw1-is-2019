package tiw1.client.rabbitClient;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;
import tiw1.service.ConfirmationService;

@Service
@RabbitListener(queues = "${rabbit.queue-name}")
public class RabbitmqConsumer {
    private Logger LOGGER = LoggerFactory.getLogger(RabbitmqConsumer.class);

    private ConfirmationService confirmationService;

    public RabbitmqConsumer(ConfirmationService confirmationService) {
        this.confirmationService = confirmationService;
    }

    @RabbitHandler
    public void consumer(String activationNumber) {
        LOGGER.info("received {} ", activationNumber);
        confirmationService.activateEmprunt(activationNumber);
    }
}
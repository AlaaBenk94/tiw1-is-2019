package tiw1.service.emprunt;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import tiw1.dto.TrottinetteDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.repository.EmpruntRepository;
import tiw1.service.TrottinetteService;

import javax.transaction.Transactional;
import java.sql.Timestamp;
import java.time.Instant;

/**
 * delete unpaid {@link tiw1.domain.Emprunt} and release their {@link tiw1.domain.Trottinette}
 */
@Component
public class EmpruntCleanerService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntCleanerService.class);

    private EmpruntRepository empruntRepository;
    private TrottinetteService trottinetteService;

    @Value("${cleaning.range}")
    private long cleaningRange;

    public EmpruntCleanerService(EmpruntRepository empruntRepository, TrottinetteService trottinetteService) {
        this.empruntRepository = empruntRepository;
        this.trottinetteService = trottinetteService;
    }

    @Async
    @Transactional
    @Scheduled(fixedRateString = "${cleaning.rate}")
    public void cleanEmpruntTable() {
        LOGGER.info("Cleaner Started... [ " + Instant.now() + "]");
        empruntRepository.deleteAllByActivatedFalseAndDateBefore(Timestamp.from(Instant.now().minusSeconds(cleaningRange)))
                .forEach(emprunt -> {
                    LOGGER.info("DELETED EMPRUNT({}, {}, {}, {})", emprunt.getId(), emprunt.getActivated(), emprunt.getDate(), emprunt.getActivationNumber());
                    // reactivating trottinettes
                    try {
                        TrottinetteDto trottinette = trottinetteService.getTrottinette(emprunt.getIdTrottinette());
                        trottinetteService.updateTrottinette(TrottinetteDto.builder()
                                .withId(trottinette.getId())
                                .withIntervention(trottinette.getInterventions())
                                .withDisponible(true)
                                .build());
                    } catch (ResourceNotFoundException e) {
                        LOGGER.error("can't fetch trottinette with id {}", emprunt.getIdTrottinette());
                    }
                });

    }

}

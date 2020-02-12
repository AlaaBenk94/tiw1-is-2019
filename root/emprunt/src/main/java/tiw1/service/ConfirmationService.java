package tiw1.service;

import fr.univ_lyon1.tiw1_is.confirmation.service.ConfirmationResponse;
import fr.univ_lyon1.tiw1_is.confirmation.service.ObjectFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataAccessResourceFailureException;
import org.springframework.stereotype.Service;
import tiw1.domain.Emprunt;
import tiw1.exception.ResourceNotFoundException;
import tiw1.repository.EmpruntRepository;

@Service
public class ConfirmationService {
    private static Logger LOGGER = LoggerFactory.getLogger(ConfirmationService.class);

    private EmpruntRepository empruntRepository;
    private TrottinetteService trottinetteService;

    public ConfirmationService(EmpruntRepository empruntRepository, TrottinetteService trottinetteService) {
        this.empruntRepository = empruntRepository;
        this.trottinetteService = trottinetteService;
    }

    /**
     * verify if the requested ativation number exists then activate the related {@link tiw1.domain.Emprunt}
     * else return an error message
     *
     * @param confirmationNumber emprunt confirmation number
     * @return
     */
    public ConfirmationResponse activateEmprunt(String confirmationNumber) throws DataAccessResourceFailureException, ResourceNotFoundException {
        ConfirmationResponse confirmationResponse = new ObjectFactory().createConfirmationResponse();
        confirmationResponse.setState(200);
        confirmationResponse.setMessage("Emprunt is ativated");

        Emprunt emprunt = empruntRepository.findEmpruntByActivationNumber(confirmationNumber);
        if (emprunt != null && !emprunt.isActivated()) {
            // Edit emprunt status and save it
            emprunt.setActivated(true);
            empruntRepository.save(emprunt);
        } else {
            LOGGER.error("Can't find emprunt with activation code {}", confirmationNumber);
            confirmationResponse.setState(500);
            confirmationResponse.setMessage("Can't find emprunt with activation code " + confirmationNumber);
        }

        return confirmationResponse;
    }

}

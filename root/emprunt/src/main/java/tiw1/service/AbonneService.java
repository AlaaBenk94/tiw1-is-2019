package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tiw1.domain.Abonne;
import tiw1.dto.AbonneDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.exception.UnauthorizedAccessException;
import tiw1.repository.AbonneRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AbonneService {
    private static final Logger LOGGER = LoggerFactory.getLogger(AbonneService.class);

    private AbonneRepository abonneRepository;

    public AbonneService(AbonneRepository abonneRepository) {
        this.abonneRepository = abonneRepository;
    }

    /**
     * get abonne by id
     *
     * @param id      the id of abonne
     * @param isAdmin whether the user is admin or not
     * @param owner   the user name
     * @return an {@link AbonneDto}
     * @throws ResourceNotFoundException
     */
    public AbonneDto getAbonneById(Long id, Boolean isAdmin, String owner) throws ResourceNotFoundException, UnauthorizedAccessException {
        Abonne abonne = getAbonneFromRepository(id);

        if (!isAdmin && !abonne.getOwner().equals(owner)) {
            LOGGER.debug("the user {} does not have access to abonne with id {}", owner, id);
            throw new UnauthorizedAccessException("user " + owner + " does not have acces to abonne with id " + id);
        }

        return AbonneDto.builder()
                .withId(abonne.getId())
                .withName(abonne.getName())
                .withDateDebut(abonne.getDateDebut())
                .withDateFin(abonne.getDateFin())
                .build();
    }

    /**
     * get abonne from repository
     *
     * @param id id of abonne
     * @return {@link Abonne}
     */
    private Abonne getAbonneFromRepository(Long id) {
        return abonneRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.debug("can't find abonne with id {}", id);
                    throw new ResourceNotFoundException("can'i find abonne with id " + id);
                });
    }

    /**
     * save an Abonne to database
     *
     * @param abonneDto abonne to save
     * @return the saved abonne
     */
    public AbonneDto subscribe(AbonneDto abonneDto, String owner) {
        Abonne abonne = new Abonne(
                abonneDto.getId(),
                abonneDto.getName(),
                abonneDto.getDateDebut(),
                abonneDto.getDateFin());
        abonne.setOwner(owner);
        Abonne savedAbonne = abonneRepository.save(abonne);
        return AbonneDto.builder()
                .withId(savedAbonne.getId())
                .withName(savedAbonne.getName())
                .withDateDebut(savedAbonne.getDateDebut())
                .withDateFin(savedAbonne.getDateFin())
                .build();
    }

    /**
     * unsubscribe an abonne by setting attribute dateFin de {@link Abonne} to Now.
     *
     * @param id
     * @param isAdmin
     * @param owner
     * @return
     */
    public AbonneDto unsubscribe(Long id, Boolean isAdmin, String owner) throws ResourceNotFoundException, UnauthorizedAccessException {
        Abonne abonne = getAbonneFromRepository(id);

        if (!isAdmin && !abonne.getOwner().equals(owner)) {
            LOGGER.debug("the user {} does not have access to abonne with id {}", owner, id);
            throw new UnauthorizedAccessException("user " + owner + " does not have acces to abonne with id " + id);
        }

        abonneRepository.delete(abonne);

        return AbonneDto.builder()
                .withId(abonne.getId())
                .withName(abonne.getName())
                .withDateDebut(abonne.getDateDebut())
                .withDateFin(abonne.getDateFin())
                .build();
    }

    /**
     * get all abonne list
     *
     * @return a {@link List} of abonne
     */
    public List<AbonneDto> getAbonneList() {
        List<AbonneDto> abonneDtoList = new ArrayList();
        abonneRepository.findAll().forEach(abonne -> {
            AbonneDto abonneDto = AbonneDto.builder()
                    .withId(abonne.getId())
                    .withName(abonne.getName())
                    .withDateDebut(abonne.getDateDebut())
                    .withDateFin(abonne.getDateFin())
                    .build();

            abonneDtoList.add(abonneDto);
        });
        return abonneDtoList;
    }
}

package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tiw1.domain.Abonne;
import tiw1.dto.AbonneDto;
import tiw1.exception.ResourceNotFoundException;
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
     * @param id
     * @return
     */
    public AbonneDto getAbonneById(Long id) throws ResourceNotFoundException {
        Abonne abonne = abonneRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.debug("can't find abonne with id {}", id);
                    throw new ResourceNotFoundException("can'i find abonne with id " + id);
                });

        return AbonneDto.builder()
                .withId(abonne.getId())
                .withName(abonne.getName())
                .withDateDebut(abonne.getDateDebut())
                .withDateFin(abonne.getDateFin())
                .build();
    }

    /**
     * @param name
     * @return
     */
    public AbonneDto getAbonneByName(String name) throws ResourceNotFoundException {
        Abonne abonne = abonneRepository.findAbonneByName(name)
                .orElseGet(() -> {
                    LOGGER.debug("can't find abonne with name {}", name);
                    throw new ResourceNotFoundException("can'i find abonne with name " + name);
                });

        return AbonneDto.builder()
                .withId(abonne.getId())
                .withName(abonne.getName())
                .withDateDebut(abonne.getDateDebut())
                .withDateFin(abonne.getDateFin())
                .build();
    }

    /**
     * save an Abonne to database
     *
     * @param abonneDto abonne to save
     * @return the saved abonne
     */
    public AbonneDto saveAbonne(AbonneDto abonneDto) {
        Abonne abonne = new Abonne(
                abonneDto.getId(),
                abonneDto.getName(),
                abonneDto.getDateDebut(),
                abonneDto.getDateFin());
        Abonne savedAbonne = abonneRepository.save(abonne);
        return AbonneDto.builder()
                .withId(savedAbonne.getId())
                .withName(savedAbonne.getName())
                .withDateDebut(savedAbonne.getDateDebut())
                .withDateFin(savedAbonne.getDateFin())
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

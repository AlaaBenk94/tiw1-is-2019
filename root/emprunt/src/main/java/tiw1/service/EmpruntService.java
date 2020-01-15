package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tiw1.domain.Emprunt;
import tiw1.dto.EmpruntDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.exception.UnauthorizedAccessException;
import tiw1.repository.EmpruntRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class EmpruntService {
    private static final Logger LOGGER = LoggerFactory.getLogger(EmpruntService.class);

    private EmpruntRepository empruntRepository;

    public EmpruntService(EmpruntRepository empruntRepository) {
        this.empruntRepository = empruntRepository;
    }

    /**
     * get emprunt by id
     *
     * @param id      id of emprunt to get
     * @param isAdmin whether the user is admin or not
     * @param owner   the name of user
     * @return an {@link EmpruntDto}
     * @throws ResourceNotFoundException
     */
    public EmpruntDto getEmprunt(Long id, Boolean isAdmin, String owner) throws ResourceNotFoundException {
        Emprunt emprunt = empruntRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.debug("emprunt with id {} not found", id);
                    throw new ResourceNotFoundException("emprunt with id " + id + " not found");
                });

        if (!isAdmin && !emprunt.getOwner().equals(owner)) {
            LOGGER.debug("the user {} does not have access to emprunt with id {}", owner, id);
            throw new UnauthorizedAccessException("user " + owner + " does not have acces to emprunt with id " + id);
        }

        return EmpruntDto.builder()
                .withId(emprunt.getId())
                .withIdAbonne(emprunt.getIdAbonne())
                .withIdTrottinette(emprunt.getIdTrottinette())
                .withDate(emprunt.getDate())
                .build();
    }

    /**
     * save a new emprunt
     *
     * @param empruntDto
     * @param owner
     * @return
     */
    public EmpruntDto saveEmprunt(EmpruntDto empruntDto, String owner) {
        Emprunt emprunt = new Emprunt(
                empruntDto.getId(),
                empruntDto.getDate(),
                empruntDto.getIdAbonne(),
                empruntDto.getIdTrottinette());
        emprunt.setOwner(owner);
        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        return EmpruntDto.builder()
                .withId(savedEmprunt.getId())
                .withDate(savedEmprunt.getDate())
                .withIdTrottinette(savedEmprunt.getIdTrottinette())
                .withIdAbonne(savedEmprunt.getIdAbonne())
                .build();
    }

    /**
     * get all available emprunts
     *
     * @return {@link List} of {@link EmpruntDto}
     */
    public List<EmpruntDto> getEmprunts() {
        List<EmpruntDto> empruntDtoList = new ArrayList<>();
        empruntRepository.findAll().forEach(emprunt -> {
            EmpruntDto empruntDto = EmpruntDto.builder()
                    .withId(emprunt.getId())
                    .withDate(emprunt.getDate())
                    .withIdAbonne(emprunt.getIdAbonne())
                    .withIdTrottinette(emprunt.getIdTrottinette())
                    .build();
            empruntDtoList.add(empruntDto);
        });
        return empruntDtoList;
    }
}

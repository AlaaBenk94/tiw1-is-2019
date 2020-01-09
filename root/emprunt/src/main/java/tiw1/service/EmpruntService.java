package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tiw1.domain.Emprunt;
import tiw1.dto.EmpruntDto;
import tiw1.exception.ResourceNotFoundException;
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
     * @param id
     * @return
     * @throws ResourceNotFoundException
     */
    public EmpruntDto getEmprunt(Long id) throws ResourceNotFoundException {
        Emprunt emprunt = empruntRepository.findById(id)
                .orElseGet(() -> {
                    LOGGER.debug("emprunt with id {} not found", id);
                    throw new ResourceNotFoundException("emprunt with id " + id + " not found");
                });

        return EmpruntDto.builder()
                .withId(emprunt.getId())
                .withIdAbonne(emprunt.getIdAbonne())
                .withIdTrottinette(emprunt.getIdTrottinette())
                .withDate(emprunt.getDate())
                .build();
    }

    /**
     * @param empruntDto
     * @return
     */
    public EmpruntDto saveEmprunt(EmpruntDto empruntDto) {
        Emprunt emprunt = new Emprunt(
                empruntDto.getId(),
                empruntDto.getDate(),
                empruntDto.getIdAbonne(),
                empruntDto.getIdTrottinette());

        Emprunt savedEmprunt = empruntRepository.save(emprunt);
        return EmpruntDto.builder()
                .withId(savedEmprunt.getId())
                .withDate(savedEmprunt.getDate())
                .withIdTrottinette(savedEmprunt.getIdTrottinette())
                .withIdAbonne(savedEmprunt.getIdAbonne())
                .build();
    }

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

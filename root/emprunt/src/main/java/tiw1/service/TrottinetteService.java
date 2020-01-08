package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import tiw1.domain.Intervention;
import tiw1.domain.Trottinette;
import tiw1.dto.InterventionDto;
import tiw1.dto.TrottinetteDto;
import tiw1.exception.ResourceNotFoundException;
import tiw1.repository.TrottinetteLoader;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrottinetteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrottinetteService.class);

    private TrottinetteLoader trottinetteLoader;

    public TrottinetteService(TrottinetteLoader trottinetteLoader) {
        this.trottinetteLoader = trottinetteLoader;
    }

    public TrottinetteDto getTrottinette(Long id) {
        Trottinette trottinette = trottinetteLoader.getTrottinette(id);
        if (trottinette == null) {
            LOGGER.debug("trottinette avec l'id {} n'existe pas", id);
            throw new ResourceNotFoundException("trottinette avec l'id " + id + " n'existe pas");
        }

        return TrottinetteDto.builder()
                .withId(trottinette.getId())
                .withIntervention(buildInterventions(trottinette.getInterventions()))
                .withDisponible(trottinette.isDisponible())
                .build();
    }

    public List<TrottinetteDto> getTrottinettes() {
        return trottinetteLoader.getTrottinettes().values().stream()
                .map(trottinette ->
                        TrottinetteDto.builder()
                                .withId(trottinette.getId())
                                .withIntervention(buildInterventions(trottinette.getInterventions()))
                                .withDisponible(trottinette.isDisponible())
                                .build()
                ).collect(Collectors.toList());
    }

    private List<InterventionDto> buildInterventions(List<Intervention> interventions) {
        return interventions.stream()
                .map(intervention ->
                        InterventionDto.builder()
                                .withId(intervention.getId())
                                .withDate(intervention.getDate())
                                .withDescription(intervention.getDescription())
                                .build()
                ).collect(Collectors.toList());
    }
}

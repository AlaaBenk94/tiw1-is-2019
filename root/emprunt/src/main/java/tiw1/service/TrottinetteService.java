package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tiw1.config.MaintenanceServerProperties;
import tiw1.domain.Intervention;
import tiw1.domain.Trottinette;
import tiw1.dto.InterventionDto;
import tiw1.dto.TrottinetteDto;
import tiw1.exception.ResourceNotFoundException;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class TrottinetteService {

    private static final Logger LOGGER = LoggerFactory.getLogger(TrottinetteService.class);

    private final MaintenanceServerProperties maintenanceServerProperties;
    private final RestTemplate restTemplate;

    public TrottinetteService(MaintenanceServerProperties maintenanceServerProperties, RestTemplate restTemplate) {
        this.maintenanceServerProperties = maintenanceServerProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * retrieve a trottinette that has the requested id
     *
     * @param id id of trottinette to retrieve
     * @return a {@link TrottinetteDto}
     */
    public TrottinetteDto getTrottinette(Long id) throws ResourceNotFoundException {
        String URL = UriComponentsBuilder
                .fromHttpUrl(maintenanceServerProperties.getUrl())
                .path("trottinette/{id}")
                .buildAndExpand(id)
                .toString();
        LOGGER.info("Get trottinette from {}.... ", URL);

        ResponseEntity<Trottinette> responseEntity;
        try {
            responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    null,
                    Trottinette.class);
        } catch (Exception e) {
            LOGGER.error("There was an error when fetching trottinette with id {}", id);
            throw new ResourceNotFoundException("Can't find trottinette with id " + id);
        }

        if (!responseEntity.hasBody()) {
            LOGGER.debug("trottinette with id {} not found", id);
            throw new ResourceNotFoundException("trottinette with id " + id + " not found");
        }

        Trottinette trottinette = responseEntity.getBody();
        return TrottinetteDto.builder()
                .withId(trottinette.getId())
                .withIntervention(buildInterventions(trottinette.getInterventions()))
                .withDisponible(trottinette.isDisponible())
                .build();
    }

    /**
     * retrieve a list of trottinetes
     *
     * @return a {@link List} of all {@link TrottinetteDto}
     */
    public List<TrottinetteDto> getTrottinettes() {

        String URL = UriComponentsBuilder
                .fromHttpUrl(maintenanceServerProperties.getUrl())
                .path("trottinette/")
                .build()
                .toString();
        LOGGER.info("Getting trottinettes from {}.... ", URL);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<TrottinetteDto> requestEntity = new HttpEntity<>(requestHeaders);

        ResponseEntity<Trottinette[]> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.GET,
                requestEntity,
                Trottinette[].class);

        return Arrays.asList(responseEntity.getBody()).stream()
                .map(trottinette -> TrottinetteDto.builder()
                        .withId(trottinette.getId())
                        .withIntervention(buildInterventions(trottinette.getInterventions()))
                        .withDisponible(trottinette.isDisponible())
                        .build())
                .collect(Collectors.toList());
    }

    public void updateTrottinette(TrottinetteDto trottinetteDto) {

        String URL = UriComponentsBuilder
                .fromHttpUrl(maintenanceServerProperties.getUrl())
                .path("trottinette/{id}")
                .buildAndExpand(trottinetteDto.getId())
                .toString();
        LOGGER.info("Get trottinette from {}.... ", URL);

        HttpHeaders requestHeaders = new HttpHeaders();
        requestHeaders.setContentType(MediaType.APPLICATION_JSON);
        requestHeaders.setAccept(Arrays.asList(MediaType.APPLICATION_JSON));
        HttpEntity<TrottinetteDto> requestEntity = new HttpEntity<>(trottinetteDto, requestHeaders);

        ResponseEntity<Trottinette> responseEntity = restTemplate.exchange(
                URL,
                HttpMethod.PUT,
                requestEntity,
                Trottinette.class);

        if (!responseEntity.hasBody()) {
            LOGGER.debug("can't update trottinette with id {}", trottinetteDto.getId());
            throw new ResourceNotFoundException("can't update trottinette with id " + trottinetteDto.getId());
        }
    }

    /**
     * build a list of {@link InterventionDto} from a list of {@link Intervention}
     *
     * @param interventions a list of {@link Intervention}
     * @return a list of {@link InterventionDto}
     */
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

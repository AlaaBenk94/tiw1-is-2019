package tiw1.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tiw1.config.MaintenanceServerProperties;
import tiw1.domain.Trottinette;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TrottinetteLoaderService {
    private final Logger LOGGER = LoggerFactory.getLogger(TrottinetteLoaderService.class);

    private static Map<Long, Trottinette> trottinettes;
    private final MaintenanceServerProperties maintenanceServerProperties;
    private final RestTemplate restTemplate;

    public TrottinetteLoaderService(MaintenanceServerProperties maintenanceServerProperties,
                                    RestTemplate restTemplate) {
        this.maintenanceServerProperties = maintenanceServerProperties;
        this.restTemplate = restTemplate;
    }

    public Map<Long, Trottinette> getTrottinettes() {
        return trottinettes;
    }

    @PostConstruct
    public void loadTrottinettes() {
        try {
            String URL = UriComponentsBuilder
                    .fromHttpUrl(maintenanceServerProperties.getUrl())
                    .path("trottinette/")
                    .build()
                    .toString();
            LOGGER.info("Loading trottinettes from {}.... ", URL);

            ResponseEntity<Trottinette[]> responseEntity = restTemplate.exchange(
                    URL,
                    HttpMethod.GET,
                    null,
                    Trottinette[].class);
            trottinettes = Arrays.asList(responseEntity.getBody()).stream()
                    .collect(Collectors.toMap(Trottinette::getId, trottinette -> trottinette));

        } catch (HttpClientErrorException e) {
            LOGGER.error("Error when with HttpClient when calling {}", maintenanceServerProperties.getUrl());
        } catch (RestClientException e) {
            LOGGER.error("Error when with RestClient when calling {}", maintenanceServerProperties.getUrl());
        }
    }
}

package tiw1.repository.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestClientException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import tiw1.config.MaintenanceServerProperties;
import tiw1.domain.Trottinette;
import tiw1.repository.TrottinetteLoader;

import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class TrottinetteLoaderImpl implements TrottinetteLoader, InitializingBean {
    private final Logger LOGGER = LoggerFactory.getLogger(TrottinetteLoaderImpl.class);

    private static Map<Long, Trottinette> trottinettes;
    private final MaintenanceServerProperties maintenanceServerProperties;
    private final RestTemplate restTemplate;

    public TrottinetteLoaderImpl(MaintenanceServerProperties maintenanceServerProperties,
                                 RestTemplate restTemplate) {
        this.maintenanceServerProperties = maintenanceServerProperties;
        this.restTemplate = restTemplate;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Map<Long, Trottinette> getTrottinettes() {
        return trottinettes;
    }

    /**
     * {@inheritDoc}
     */
    @Override
    public Trottinette getTrottinette(Long id) {
        if (trottinettes.containsKey(id)) {
            return trottinettes.get(id);
        }

        return null;
    }

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

    @Override
    public void afterPropertiesSet() throws Exception {
        loadTrottinettes();
    }
}

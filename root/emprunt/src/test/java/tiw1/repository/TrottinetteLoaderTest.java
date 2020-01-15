package tiw1.repository;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import tiw1.config.MaintenanceServerProperties;
import tiw1.domain.Trottinette;
import tiw1.repository.impl.TrottinetteLoaderImpl;

import static org.mockito.Mockito.verify;

@ExtendWith(MockitoExtension.class)
class TrottinetteLoaderTest {

    private static final String URL = "http://localhost:8080/";

    public TrottinetteLoaderTest() {
    }

    @Mock
    private MaintenanceServerProperties maintenanceServerProperties;

    @Mock
    private RestTemplate restTemplate;

    private TrottinetteLoader trottinetteLoader;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        trottinetteLoader = new TrottinetteLoaderImpl(maintenanceServerProperties, restTemplate);
    }

    @Test
    void getTrottinettes() {
        Trottinette[] trottinettes = new Trottinette[]{new Trottinette(15, true)};
        Mockito.when(maintenanceServerProperties.getUrl())
                .thenReturn(URL);
        Mockito.when(restTemplate.exchange(URL + "trottinette/", HttpMethod.GET, null, Trottinette[].class))
                .thenReturn(new ResponseEntity<>(trottinettes, HttpStatus.OK));

        ((TrottinetteLoaderImpl) trottinetteLoader).loadTrottinettes();
        Assertions.assertEquals(trottinettes[0], trottinetteLoader.getTrottinettes().get(15L));

        verify(restTemplate).exchange(URL + "trottinette/", HttpMethod.GET, null, Trottinette[].class);
    }
}

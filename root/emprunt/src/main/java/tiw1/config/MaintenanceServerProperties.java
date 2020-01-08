package tiw1.config;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * configuration de serveur des trottinettes
 */
@Configuration
@ConfigurationProperties("maintenance")
public class MaintenanceServerProperties {

    /**
     * l'url de l'endpoint rest de serveur
     */
    private String url;

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }
}

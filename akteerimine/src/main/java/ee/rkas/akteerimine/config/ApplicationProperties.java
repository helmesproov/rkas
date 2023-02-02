package ee.rkas.akteerimine.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Akteerimine.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Mail mail = new Mail();

    @Getter
    @Setter
    public static class Mail {

        private String from = "";

        private String baseUrl = "";
    }
}

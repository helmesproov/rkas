package ee.rkas.lepinguregister.config;

import lombok.Getter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Lepinguregister.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@Getter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Mail mail = new Mail();

    @Getter
    public static class Mail {

        private String from = "";

        private String baseUrl = "";
    }
}

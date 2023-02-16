package ee.rkas.lepinguregister.config;

import lombok.Getter;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * Properties specific to Lepinguregister.
 * <p>
 * Properties are configured in the {@code application.yml} file.
 * See {@link tech.jhipster.config.JHipsterProperties} for a good example.
 */
@Getter
@Setter
@ConfigurationProperties(prefix = "application", ignoreUnknownFields = false)
public class ApplicationProperties {

    private final Mail mail = new Mail();

    @Getter
    @Setter
    public static class Mail {

        // Set address to
        private String to = "oliver-erik.suik@helmes.com";

        private String from = "helmes.proov@gmail.com";

        private String baseUrl = "";
    }
}

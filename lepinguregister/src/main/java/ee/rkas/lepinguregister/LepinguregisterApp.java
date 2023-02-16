package ee.rkas.lepinguregister;

import com.fasterxml.jackson.databind.ObjectMapper;
import ee.rkas.lepinguregister.config.ApplicationProperties;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;
import org.springframework.jms.support.converter.MappingJackson2MessageConverter;
import org.springframework.jms.support.converter.MessageConverter;
import org.springframework.jms.support.converter.MessageType;
import org.springframework.scheduling.annotation.EnableAsync;
import tech.jhipster.config.DefaultProfileUtil;

@SpringBootApplication
@EnableConfigurationProperties({ LiquibaseProperties.class, ApplicationProperties.class })
@EnableAsync
public class LepinguregisterApp {

    private final Environment env;

    public LepinguregisterApp(Environment env) {
        this.env = env;
    }

    public static void main(String[] args) {
        SpringApplication app = new SpringApplication(LepinguregisterApp.class);
        DefaultProfileUtil.addDefaultProfile(app);
        Environment env = app.run(args).getEnvironment();
    }

    @Bean
    public MessageConverter jacksonJmsMessageConverter(ObjectMapper aObjectMapper) {
        MappingJackson2MessageConverter converter = new MappingJackson2MessageConverter();
        converter.setObjectMapper(aObjectMapper);
        converter.setTargetType(MessageType.TEXT);
        converter.setTypeIdPropertyName("_type");
        return converter;
    }
}

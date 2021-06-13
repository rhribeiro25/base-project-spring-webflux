package br.com.rhribeiro25.baseprojectspringwebflux.config;

import lombok.extern.slf4j.Slf4j;
import org.flywaydb.core.Flyway;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;

/**
 * Flyway configuration class
 *
 * @author Renan Henrique Ribeiro
 * @since 06/10/2021
 */
@Configuration
@Slf4j
public class FlywayConfig {

    private final Environment env;

    public FlywayConfig(final Environment env) {
        this.env = env;
    }

    @Bean(initMethod = "migrate")
    public Flyway flyway() {
        var flyway =
                new Flyway(
                        Flyway.configure()
                                .baselineOnMigrate(false)
                                .dataSource(
                                        env.getRequiredProperty("spring.flyway.url"),
                                        env.getRequiredProperty("spring.flyway.user"),
                                        env.getRequiredProperty("spring.flyway.password")));

        log.info("FLYWAY LOCATIONS IS {}", flyway.getConfiguration().getLocations()[0].getPath());
        return flyway;
    }
}
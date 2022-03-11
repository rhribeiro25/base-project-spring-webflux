package br.com.rhribeiro25.baseprojectspringwebflux;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.data.r2dbc.config.EnableR2dbcAuditing;
import org.springframework.data.r2dbc.repository.config.EnableR2dbcRepositories;

/**
 * Class main
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
@EnableR2dbcAuditing
@SpringBootApplication
@EnableR2dbcRepositories(basePackages = {"br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.postgresql.*"})
@EnableMongoRepositories(basePackages = {"br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.mongodb.*"})
public class BaseProjectSpringWebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseProjectSpringWebfluxApplication.class, args);
        log.info("🚀 Server ready at http://localhost:9090/api");
    }
}

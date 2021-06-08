package br.com.rhribeiro25.baseprojectspringwebflux;

import lombok.extern.log4j.Log4j2;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class main
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Log4j2
@SpringBootApplication
public class BaseProjectSpringWebfluxApplication {
    public static void main(String[] args) {
        SpringApplication.run(BaseProjectSpringWebfluxApplication.class, args);
        log.info("🚀 Server ready at http://localhost:9090/api");
    }
}

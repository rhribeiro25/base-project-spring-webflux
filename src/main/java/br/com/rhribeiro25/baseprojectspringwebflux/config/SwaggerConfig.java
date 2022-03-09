package br.com.rhribeiro25.baseprojectspringwebflux.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.parser.OpenAPIV3Parser;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * Swagger configuration class
 *
 * @author Renan Henrique Ribeiro
 * @since 03/09/2022
 */
@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        return new OpenAPIV3Parser().read("files/swagger/swagger.json");
    }
}

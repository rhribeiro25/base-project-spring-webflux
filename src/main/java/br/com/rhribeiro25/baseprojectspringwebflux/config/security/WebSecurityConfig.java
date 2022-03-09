package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
import reactor.core.publisher.Mono;

import java.util.Arrays;

/**
 * WebSecurity configuration class
 *
 * @author Renan Henrique Ribeiro
 * @since 08/02/2022
 */

@Log4j2
@Configuration
@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private SecurityContextRepository securityContextRepository;

    @Value("${allowed.origin}")
    private String ALLOWED_ORIGIN;

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
        return http
                .exceptionHandling()
                .authenticationEntryPoint((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                ))
                .accessDeniedHandler((swe, e) -> Mono.fromRunnable(
                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                ))
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                // Swagger
                .pathMatchers(HttpMethod.GET, "/swagger-ui.html").permitAll()
                .pathMatchers(HttpMethod.GET, "/webjars/**").permitAll()
                .pathMatchers(HttpMethod.GET, "/v3/api-docs/**").permitAll()
                // Authentication
                .pathMatchers(HttpMethod.POST, "/api/auths/login").permitAll()
                .pathMatchers(HttpMethod.POST, "/api/auths/logout").authenticated()
                // User CRUD
                .pathMatchers(HttpMethod.GET, "/api/users*").authenticated()
                .pathMatchers(HttpMethod.GET, "/api/users/*").authenticated()
                .pathMatchers(HttpMethod.POST, "/api/users").hasAuthority("ADMIN")
                .pathMatchers(HttpMethod.PUT, "/api/users").hasAuthority("ADMIN")
                .pathMatchers(HttpMethod.PATCH, "/api/users/*").hasAuthority("ADMIN")
                .pathMatchers(HttpMethod.DELETE, "/api/users/*").hasAuthority("ADMIN")
                // Address
                .pathMatchers(HttpMethod.GET, "/api/addresses*").authenticated()
                .and()
                .cors()
                .and()
                .build();
    }

    @Bean
    public CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGIN));
        configuration.setAllowedHeaders(Arrays.asList("*"));
        configuration.setAllowedMethods(Arrays.asList("*"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/api/**", configuration);
        return source;
    }

}

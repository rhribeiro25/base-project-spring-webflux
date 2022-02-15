//package br.com.rhribeiro25.baseprojectspringwebflux.config.security;
//
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.context.annotation.Bean;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.core.env.Environment;
//import org.springframework.http.HttpMethod;
//import org.springframework.security.config.Customizer;
//import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
//import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
//import org.springframework.security.config.web.server.ServerHttpSecurity;
//import org.springframework.security.core.userdetails.MapReactiveUserDetailsService;
//import org.springframework.security.core.userdetails.User;
//import org.springframework.security.core.userdetails.UserDetails;
//import org.springframework.security.crypto.factory.PasswordEncoderFactories;
//import org.springframework.security.crypto.password.PasswordEncoder;
//import org.springframework.security.web.server.SecurityWebFilterChain;
//import org.springframework.web.cors.CorsConfiguration;
//import org.springframework.web.cors.reactive.CorsConfigurationSource;
//import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;
//
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collection;
//
///**
// * WebFlux configuration class
// *
// * @author Renan Henrique Ribeiro
// * @since 08/02/2022
// */
//
//@Slf4j
//@Configuration
//@EnableWebFluxSecurity
//@EnableReactiveMethodSecurity
//public class WebSecurityConfig {
//
//    private static final PasswordEncoder encoder = PasswordEncoderFactories.createDelegatingPasswordEncoder();
//
//    private String ALLOWED_ORIGIN;
//
//    @Autowired
//    public WebSecurityConfig(Environment env) {
//        ALLOWED_ORIGIN = env.getProperty("allowed.origin");
//    }
//
//    @Bean
//    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity http) {
//        return http
//                .authorizeExchange()
//                .pathMatchers(HttpMethod.GET, "/api/users/*").authenticated()
//                .pathMatchers(HttpMethod.POST, "/api/users").hasRole("ADMIN")
//                .pathMatchers(HttpMethod.PUT, "/api/users").hasRole("ADMIN")
//                .pathMatchers(HttpMethod.PATCH, "/api/users").hasRole("ADMIN")
//                .pathMatchers(HttpMethod.DELETE, "/api/users").hasRole("ADMIN")
//                .pathMatchers(HttpMethod.GET, "/api/addresses*").authenticated().and()
//                .csrf().disable()
//                .cors().and()
//                .httpBasic(Customizer.withDefaults())
//                .formLogin(Customizer.withDefaults())
//                .build();
//    }
//
//    @Bean
//    public CorsConfigurationSource corsConfigurationSource() {
//        CorsConfiguration configuration = new CorsConfiguration();
//        configuration.setAllowedOrigins(Arrays.asList(ALLOWED_ORIGIN));
//        configuration.setAllowedHeaders(Arrays.asList("*"));
//        configuration.setAllowedMethods(Arrays.asList("*"));
//        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
//        source.registerCorsConfiguration("/api/**", configuration);
//        return source;
//    }
//
//    @Bean
//    public MapReactiveUserDetailsService mapReactiveUserDetailsService() {
//        return new MapReactiveUserDetailsService(users);
//    }
//
//    private static UserDetails user(String user, String password, String... roles) {
//        return User.builder()
//                .username(user)
//                .password(password)
//                .passwordEncoder(encoder::encode)
//                .roles(roles)
//                .build();
//    }
//
//    private static final Collection<UserDetails> users = new ArrayList<>(
//            Arrays.asList(
//                    user("thor", "raios","ADMIN"),
//                    user("loki", "magia","USER"),
//                    user("zeus", "sabedoria","ADMIN", "USER")
//            ));
//
//}

package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.crypto.password.NoOpPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.server.SecurityWebFilterChain;
import reactor.core.publisher.Mono;

@EnableWebFluxSecurity
@EnableReactiveMethodSecurity
public class WebSecurityConfig {
    private final AuthenticationManager authenticationManager;
    private final SecurityContextRepository securityContextRepository;

    public WebSecurityConfig(AuthenticationManager authenticationManager, SecurityContextRepository securityContextRepository) {
        this.authenticationManager = authenticationManager;
        this.securityContextRepository = securityContextRepository;
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return NoOpPasswordEncoder.getInstance();
    }

    @Bean
    public SecurityWebFilterChain securityWebFilterChain(ServerHttpSecurity httpSecurity) {
        return httpSecurity
                .exceptionHandling()
                .authenticationEntryPoint(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED)
                                )
                )
                .accessDeniedHandler(
                        (swe, e) ->
                                Mono.fromRunnable(
                                        () -> swe.getResponse().setStatusCode(HttpStatus.FORBIDDEN)
                                )
                )
                .and()
                .csrf().disable()
                .formLogin().disable()
                .httpBasic().disable()
                .authenticationManager(authenticationManager)
                .securityContextRepository(securityContextRepository)
                .authorizeExchange()
                .pathMatchers("/", "/api/users/login", "/favicon.ico").permitAll()
                .pathMatchers("/controller").hasRole("ADMIN")
                .anyExchange().authenticated()
                .and()
                .build();
    }
}

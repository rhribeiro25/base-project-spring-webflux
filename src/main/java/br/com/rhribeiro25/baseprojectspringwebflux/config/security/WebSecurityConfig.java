package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import br.com.rhribeiro25.baseprojectspringwebflux.error.AuthenticationError;
import br.com.rhribeiro25.baseprojectspringwebflux.error.handler.AcessDeniedHandler;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.convert.converter.Converter;
import org.springframework.core.env.Environment;
import org.springframework.security.authorization.ReactiveAuthorizationManager;
import org.springframework.security.config.annotation.method.configuration.EnableReactiveMethodSecurity;
import org.springframework.security.config.annotation.web.reactive.EnableWebFluxSecurity;
import org.springframework.security.config.web.server.ServerHttpSecurity;
import org.springframework.security.oauth2.jwt.MappedJwtClaimSetConverter;
import org.springframework.security.oauth2.jwt.NimbusReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoder;
import org.springframework.security.oauth2.jwt.ReactiveJwtDecoders;
import org.springframework.security.web.server.SecurityWebFilterChain;
import org.springframework.security.web.server.header.XFrameOptionsServerHttpHeadersWriter.Mode;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.reactive.CorsConfigurationSource;
import org.springframework.web.cors.reactive.UrlBasedCorsConfigurationSource;

import java.util.Arrays;
import java.util.Collections;
import java.util.Map;

@Slf4j
@Configuration
@EnableReactiveMethodSecurity
@EnableWebFluxSecurity
public class WebSecurityConfig {

    private String ISSUER_URI;
    private String ALLOWED_ORIGIN;

    @Autowired
    private AuthenticationError authenticationError;

    @Autowired
    private AcessDeniedHandler acessDeniedHandler;

    @Autowired
    public WebSecurityConfig(Environment env) {
        ISSUER_URI = env.getProperty("spring.security.oauth2.resourceserver.jwk.issuer-uri");
        ALLOWED_ORIGIN = env.getProperty("allowed.origin");
    }

    @Bean
    public SecurityWebFilterChain springSecurityFilterChain(ServerHttpSecurity http) {
        http.exceptionHandling()
                .authenticationEntryPoint(authenticationError)
                .accessDeniedHandler(acessDeniedHandler).and()
                .csrf().disable()
                .formLogin().disable()
                .cors().and()
                .httpBasic().disable();
        http.oauth2ResourceServer().bearerTokenConverter(new JwtConfig()).authenticationEntryPoint(authenticationError).jwt();
        http.authorizeExchange().anyExchange().access(reactiveAuthorizationManager());
        http.headers().frameOptions().mode(Mode.SAMEORIGIN);
        return http.build();
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

    @Bean
    public ReactiveAuthorizationManager reactiveAuthorizationManager() {
        AuthorizationConfig webfluxReactiveAuthorizationConfig = new AuthorizationConfig();
        return webfluxReactiveAuthorizationConfig;
    }

    @Bean
    public ReactiveJwtDecoder reactiveJwtDecoderByIssuerUri() {
        var jwtDecoder = (NimbusReactiveJwtDecoder) ReactiveJwtDecoders.fromIssuerLocation(ISSUER_URI);
        jwtDecoder.setClaimSetConverter(new Converter<Map<String, Object>, Map<String, Object>>() {
            @Override
            public Map<String, Object> convert(Map<String, Object> claims) {
                var convertedClaims = MappedJwtClaimSetConverter.withDefaults(Collections.emptyMap()).convert(claims);
                convertedClaims.put("sub", (String) convertedClaims.get("preferred_username"));

                return convertedClaims;
            }
        });
        return jwtDecoder;
    }
}

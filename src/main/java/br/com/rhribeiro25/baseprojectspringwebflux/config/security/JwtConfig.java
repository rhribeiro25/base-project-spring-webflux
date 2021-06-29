package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import br.com.rhribeiro25.baseprojectspringwebflux.utils.JwtUtils;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.security.oauth2.core.OAuth2AuthenticationException;
import org.springframework.security.oauth2.server.resource.BearerTokenAuthenticationToken;
import org.springframework.security.oauth2.server.resource.BearerTokenError;
import org.springframework.security.oauth2.server.resource.BearerTokenErrorCodes;
import org.springframework.security.web.server.authentication.ServerAuthenticationConverter;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import javax.annotation.PostConstruct;

@Slf4j
@Component
public class JwtConfig implements ServerAuthenticationConverter {

    private static final String TOKEN_AUTHORIZATION = "Authorization";

    private static JwtUtils staticJwtUtils;

    private static String ERROR_JWT_EXPIRED = "Error: JWT expired";

    @Autowired
    private JwtUtils jwtUtils;

    @PostConstruct
    private void initStaticDao() {
        staticJwtUtils = this.jwtUtils;
    }

    public Mono convert(ServerWebExchange exchange) {
        return Mono.justOrEmpty(token(exchange.getRequest()))
                .map(token -> {
                    if (token.isEmpty() || token.contains("Error")) {
                        BearerTokenError error = invalidTokenError(token);
                        throw new OAuth2AuthenticationException(error);
                    }
                    return new BearerTokenAuthenticationToken(token);
                }).doOnNext(throwable -> Mono.just(new JwtException(ERROR_JWT_EXPIRED)));
    }

    private String token(ServerHttpRequest request) throws JwtException {
        return resolveFromHeader(request.getHeaders());
    }

    private static String resolveFromHeader(HttpHeaders httpHeaders) throws JwtException {
        String authToken = httpHeaders.getFirst(TOKEN_AUTHORIZATION);
        if (authToken != null) {
            Boolean validToken = staticJwtUtils.validTokenExpired(authToken);
            if (!validToken) return ERROR_JWT_EXPIRED;
            return authToken;
        }
        return null;
    }

    private static BearerTokenError invalidTokenError(String error) {
        return new BearerTokenError(
                BearerTokenErrorCodes.INVALID_TOKEN,
                HttpStatus.UNAUTHORIZED,
                error != null ? error : "Bearer token is malformed",
                "https://tools.ietf.org/html/rfc6750#section-3.1");
    }
}
package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.mongodb.BlackListRepository;
import io.jsonwebtoken.Claims;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.ReactiveAuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Authentication Manager Util class
 *
 * @author Renan Henrique Ribeiro
 * @since 08/02/2022
 */

@Component
@Log4j2
public class AuthenticationManager implements ReactiveAuthenticationManager {

    @Autowired
    private JwtUtil jwtUtil;

    @Autowired
    private BlackListRepository blackListRepository;

    @Override
    public Mono<Authentication> authenticate(Authentication authentication) {
        String authToken = authentication.getCredentials().toString();
        return blackListRepository.existsByToken(authToken).flatMap(existsInBlackList -> {
            String email;
            try {
                email = jwtUtil.extractEmail(authToken);
            } catch (Exception e) {
                email = null;
                log.error(e.getMessage());
            }
            if (email != null && jwtUtil.validateToken(authToken) && !existsInBlackList) {
                Claims claims = jwtUtil.getClaimsFromToken(authToken);
                List<String> role = claims.get("role", List.class);
                List<SimpleGrantedAuthority> authorities = role.stream()
                        .map(SimpleGrantedAuthority::new)
                        .collect(Collectors.toList());
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken(
                        email,
                        null,
                        authorities
                );

                return Mono.just(authenticationToken);
            } else {
                return Mono.empty();
            }
        });

    }
}

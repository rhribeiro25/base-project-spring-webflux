package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;


import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.postgresql.UserEntity;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.redis.TokenEntity;
import reactor.core.publisher.Mono;

/**
 * Interface Auth Service
 *
 * @author Renan Henrique Ribeiro
 * @since 16/02/2022
 */
public interface AuthService {
    Mono generateToken(UserEntity user);
    Mono saveTokenInBlacklist(String token);
}
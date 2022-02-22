package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;


import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Interface Auth Service
 *
 * @author Renan Henrique Ribeiro
 * @since 16/02/2022
 */
public interface AuthService {
    Mono generateToken(UserEntity user);
    Mono saveTokenInBlacklist(AuthDocument auth);
    Flux findAll();
}
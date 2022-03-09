package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;


import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestLogin;
import reactor.core.publisher.Mono;

/**
 * Interface Auth Service
 *
 * @author Renan Henrique Ribeiro
 * @since 16/02/2022
 */
public interface AuthService {
    Mono generateToken(UserRequestLogin user);

    Mono saveTokenInBlacklist(AuthDocument auth);
}
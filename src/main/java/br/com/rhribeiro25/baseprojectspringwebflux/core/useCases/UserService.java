package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPatch;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request.UserRequestPost;
import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import reactor.core.publisher.Mono;

/**
 * Interface User Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/27/2021
 */
public interface UserService extends CrudService {

    Mono verifyUserByEmailOrActivateUser(UserRequestPost createdUser);

    Mono setUserData(Long id, UserRequestPatch updatedUser);

    Mono<UserEntity> findByEmail(String email);

    Mono<Long> countByIsActivated(boolean isActivated);
}



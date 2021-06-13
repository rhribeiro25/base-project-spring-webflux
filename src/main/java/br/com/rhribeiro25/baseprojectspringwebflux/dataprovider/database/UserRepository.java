package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;

/**
 * Class User Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
public interface  UserRepository extends ReactiveCrudRepository<UserEntity, Long> {
    Mono<UserEntity> findById(Long id);
}
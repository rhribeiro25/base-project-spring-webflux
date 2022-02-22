package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.postgresql;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Class User Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Repository
public interface  UserRepository extends ReactiveSortingRepository<UserEntity, Long> {

//  @Query("SELECT * FROM users AS u WHERE u.id = :id")
    Mono<UserEntity> findByIdAndIsActivated(Long id, Boolean isActivated);

    Mono<UserEntity> findByEmail(String email);

    Flux<UserEntity> findAllByIsActivated(Pageable page, Boolean isActivated);

    Mono<Long> countByIsActivated(Boolean isActivated);

    Mono<UserEntity> save(UserEntity user);

}

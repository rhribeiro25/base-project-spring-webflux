package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.UserEntity;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;


/**
 * Class User Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
public interface  UserRepository extends ReactiveSortingRepository<UserEntity, Long> {

//  @Query("SELECT * FROM users AS u WHERE u.id = :id")
    Mono<UserEntity> findById(Long id);

    Flux<UserEntity> findAllByIdNotNullOrderByIdAsc(Pageable page);

    Mono<Long> count();

    Mono<UserEntity> save(UserEntity user);

}

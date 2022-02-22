package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.AuthEntity;
import org.springframework.data.repository.reactive.ReactiveSortingRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Class Black-List Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */

@Repository
public interface BlackListRepository extends ReactiveSortingRepository<AuthEntity, String> {
    Mono<AuthEntity> findByToken(String token);
    Mono<Boolean> existsByToken(String token);
}

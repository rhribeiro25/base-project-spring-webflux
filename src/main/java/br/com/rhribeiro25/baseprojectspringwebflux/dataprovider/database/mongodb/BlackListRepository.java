package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.mongodb;

import br.com.rhribeiro25.baseprojectspringwebflux.core.document.AuthDocument;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Mono;

/**
 * Class Black-List Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */

@Repository
public interface BlackListRepository extends ReactiveMongoRepository<AuthDocument, String> {
    Mono<AuthDocument> findByToken(String token);
    Mono<Boolean> existsByToken(String token);
}

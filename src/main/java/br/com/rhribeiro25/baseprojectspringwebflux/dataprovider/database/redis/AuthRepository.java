package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.redis;

import br.com.rhribeiro25.baseprojectspringwebflux.core.entity.redis.TokenEntity;
import lombok.RequiredArgsConstructor;
import org.springframework.data.redis.core.ReactiveRedisOperations;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

/**
 * Class Token Repository
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */

@Repository
@RequiredArgsConstructor
public class AuthRepository {
    private final ReactiveRedisOperations<String, TokenEntity> reactiveRedisOperations;

    public Flux<TokenEntity> findAll(){
        return this.reactiveRedisOperations.opsForList().range("token", 0, -1);
    }

    public Mono<TokenEntity> findById(String id) {
        return this.findAll().filter(p -> p.getId().equals(id)).last();
    }


    public Mono<Long> save(TokenEntity token){
        return this.reactiveRedisOperations.opsForList().rightPush("token", token);
    }

    public Mono<Boolean> deleteAll() {
        return this.reactiveRedisOperations.opsForList().delete("token");
    }
}

package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import org.springframework.data.domain.Pageable;
import reactor.core.publisher.Mono;

/**
 * Interface CRUD Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
public interface CrudService {
    Mono findById(Long id);
    Mono findAll(Pageable page);
    Mono save(Object obj);
    Mono updateByPut(Object obj);
    Mono updateByPatch(Long id, Object obj);
    Mono delete(Long id);
}
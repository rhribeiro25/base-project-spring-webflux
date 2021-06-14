package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.interfaces.generic;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.DataResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PageInfoResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PaginatorResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Class Exam Converter
 *
 * @author Renan Henrique Ribeiro
 * @since 06/14/2021
 */
@Slf4j
public abstract class GenericConverter {

    public final static Mono converterFluxToPaginatorResponse(Flux fluxList, Pageable page, Mono<Long> monoCount) {
        return monoCount.flatMap(count -> fluxList.collect(Collectors.toList()).flatMap(monoList -> Mono.just(PaginatorResponse.builder()
                .statusCode(HttpStatus.OK.value())
                .data(DataResponse.builder()
                        .list(Optional.ofNullable((List<Object>) monoList).orElseGet(() -> Collections.emptyList()))
                        .pageInfo(PageInfoResponse.builder()
                                .count(count)
                                .currentPage(page.getPageNumber())
                                .pageCount(monoList == null ? 0 : ((List<Object>) monoList).size())
                                .pageSize(page.getPageSize())
                                .build())
                        .build())
                .build())));
    }

    public final static Mono converterMonoToObjectResponse(Mono monoObject) {
        return monoObject.map(object -> ObjectResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(object)
                        .build())
                .switchIfEmpty(Mono.just(ObjectResponse.builder()
                        .statusCode(HttpStatus.OK.value())
                        .data(null)
                        .build()));
    }
}

package br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.adapter.generic;

import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.DataResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.ObjectResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PageInfoResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response.PaginatorResponse;
import br.com.rhribeiro25.baseprojectspringwebflux.error.exception.InternalServerErrorException;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
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

    private static ModelMapper modelMapper = new ModelMapper();

    public final static <S, T> List<T> converterListToList(List<S> source, Class<T> outputClass) {
        if (null == source || source.isEmpty())
            throw new InternalServerErrorException("A lista não pode ser vazia!");
        return source.stream().map(entity -> modelMapper.map(entity, outputClass)).collect(Collectors.toList());
    }

    public final static <S, T> T converterObjectToObject(S source, Class<T> outPutClass) {
        if (null == source)
            throw new InternalServerErrorException("O objeto não pode ser nulo!");
        return modelMapper.map(source, outPutClass);
    }

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

    public final static Mono converterMonoToObjectResponse(Mono monoObject, HttpStatus status) {
        return monoObject.map(object -> ObjectResponse.builder()
                        .statusCode(status.value())
                        .data(object)
                        .build())
                .switchIfEmpty(Mono.just(ObjectResponse.builder()
                        .statusCode(status.value())
                        .data(null)
                        .build()));
    }
}

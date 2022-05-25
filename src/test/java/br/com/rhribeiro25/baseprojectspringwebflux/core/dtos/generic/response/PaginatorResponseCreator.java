package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response;

import org.springframework.http.HttpStatus;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class PaginatorResponseCreator {
    public static PaginatorResponse createPaginatorResponse(List userEntities) {
        return PaginatorResponse.builder()
                .data(DataResponse.builder()
                        .list(userEntities)
                        .pageInfo(PageInfoResponse.builder()
                                .count(5L)
                                .currentPage(0)
                                .pageCount(1)
                                .pageSize(5)
                                .build())
                        .build())
                .statusCode(200)
                .build();
    }

}
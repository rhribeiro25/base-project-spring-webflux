package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response;

import org.springframework.http.HttpStatus;

public class ObjectResponseCreator {
    public static ObjectResponse createObjectResponse(Object object, HttpStatus status) {
        return ObjectResponse.builder()
                .statusCode(status.value())
                .data(object)
                .build();
    }

}
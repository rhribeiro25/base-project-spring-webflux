package br.com.rhribeiro25.baseprojectspringwebflux.error;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;
import java.util.List;

/**
 * Class Error
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@Builder
@Getter
public class Error implements Serializable {
    private final int statusCode;
    private final ErrorDetails error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private final List<ParamErrorDetails> params;
}

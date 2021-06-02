package br.com.rhribeiro25.baseprojectspringwebflux.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Class Param Error Details
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@Builder
@Getter
@AllArgsConstructor
public class ParamErrorDetails implements Serializable {
    private static final long serialVersionUID = -8579990290309623052L;
    private final String message;
    private final String property;
    private final String value;
}

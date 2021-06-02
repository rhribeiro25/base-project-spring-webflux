package br.com.rhribeiro25.baseprojectspringwebflux.error;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * Class Error Details
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Slf4j
@Builder
@Getter
@Data
@AllArgsConstructor
public class ErrorDetails implements Serializable {
    private static final long serialVersionUID = -4737922838431405101L;
    private final String name;
    private final String message;
    private final Long time;
}

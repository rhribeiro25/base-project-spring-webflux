package br.com.rhribeiro25.baseprojectspringwebflux.error;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.extern.slf4j.Slf4j;

/**
 * Class generic for treatment of WebClient Error Response Exception,
 * because when there is no record it returns as family 4xx
 * falling in one exception
 *
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Builder
@Slf4j
@JsonIgnoreProperties({ "cause", "stackTrace", "suppressed", "localizedMessage", "message", "status"})
public class WebClientErrorResponseException extends Throwable {
    private static final long serialVersionUID = -1042815397974261032L;
    private Integer statusCode;
    private Object error;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Object params;

    public WebClientErrorResponseException() {
    }

    public WebClientErrorResponseException(Integer statusCode, Object error, Object params) {
        super("");
        this.statusCode = statusCode;
        this.error = error;
        this.params = params;
    }

    public WebClientErrorResponseException(Integer statusCode, Object error) {
        super("");
        this.statusCode = statusCode;
        this.error = error;
    }

    public WebClientErrorResponseException(String message, Throwable cause) {
        super(message, cause);
    }

    public WebClientErrorResponseException(Throwable cause) {
        super(cause);
    }

    public WebClientErrorResponseException(String message, Throwable cause, boolean enableSuppression, boolean writableStackTrace) {
        super(message, cause, enableSuppression, writableStackTrace);
    }

}

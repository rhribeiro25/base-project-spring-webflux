package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Data Transfer Object List Paginator Response
 * @author Renan Henrique Ribeiro
 * @since 01/03/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PaginatorResponse {
    private Integer statusCode;
    private DataResponse data;
}

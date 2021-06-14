package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Data Transfer Object Patient Response
 * @author Renan Henrique Ribeiro
 * @since 01/03/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PageInfoResponse {
    private Integer currentPage;
    private Integer pageCount;
    private Integer pageSize;
    private Long count;

}
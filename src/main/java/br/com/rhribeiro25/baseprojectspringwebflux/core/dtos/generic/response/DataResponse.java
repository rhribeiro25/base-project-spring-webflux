package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.generic.response;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Class Data Transfer Object Data Response
 * @author Renan Henrique Ribeiro
 * @since 01/03/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class DataResponse {
    private List<Object> list;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private PageInfoResponse pageInfo;
}
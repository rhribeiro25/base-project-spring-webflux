package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.viacep.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Address Data Transfer Object
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressResponse {
    private String zipCode;
    private String street;
    private String complement;
    private String district;
    private String city;
}

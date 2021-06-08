package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Address Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class AddressEntity {
    private Long id;
    private String zipCode;
    private String street;
    private String number;
    private String complement;
    private String district;
    private String city;
}

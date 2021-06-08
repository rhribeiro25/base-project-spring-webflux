package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Class Company Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CompanyEntity {
    private Long id;
    private String name;
    private AddressEntity address;
    private Set<PhoneEntity> phones;
    private Set<UserEntity> users;
}

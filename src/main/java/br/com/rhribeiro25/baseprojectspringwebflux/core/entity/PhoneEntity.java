package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import br.com.rhribeiro25.baseprojectspringwebflux.core.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Set;

/**
 * Class Phone Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class PhoneEntity {
    private Long id;
    private String ddd;
    private String number;
    private PhoneType type;
}

package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import br.com.rhribeiro25.baseprojectspringwebflux.core.enums.RoleType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class Role Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class RoleEntity {
    private Long id;
    private RoleType type;
}

package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

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
    private String name;
    private String description;
}

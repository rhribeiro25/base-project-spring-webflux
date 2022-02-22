package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

/**
 * Class Black-List Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("black_list")
public class AuthEntity implements GenericEntity {

    @Id
    private Long id;

    private String token;
}

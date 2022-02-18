package br.com.rhribeiro25.baseprojectspringwebflux.core.entity.redis;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.io.Serializable;

/**
 * Class Token Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("token")
public class TokenEntity implements Serializable {

    @Id
    private String id;

    private String token;
}

package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import br.com.rhribeiro25.baseprojectspringwebflux.core.enums.PhoneType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
@Table("phones")
public class PhoneEntity {
    @Id
    private Long id;
    private String ddd;
    private String num;
    private PhoneType phoneType;
}

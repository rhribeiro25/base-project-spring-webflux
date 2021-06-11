package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Column;
import org.springframework.data.relational.core.mapping.Table;

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
@Table("companies")
public class CompanyEntity {
    @Id
    private Long id;
    private String name;
    private AddressEntity address;
    private Set<PhoneEntity> phones;
    @JsonIgnore(value = false)
    private Set<UserEntity> users;
}

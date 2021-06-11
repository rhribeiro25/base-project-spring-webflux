package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.relational.core.mapping.Table;

import java.util.Set;

/**
 * Class User Entity
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Table("users")
public class UserEntity {
    @Id
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
//    @CreatedDate
//    private Date createdAt;
//    @LastModifiedDate
//    private Date updatedAt;
    private RoleEntity role;
    private Set<PhoneEntity> phones;
    private Set<CompanyEntity> companies;
}

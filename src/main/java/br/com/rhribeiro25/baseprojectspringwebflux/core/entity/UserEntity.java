package br.com.rhribeiro25.baseprojectspringwebflux.core.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import nonapi.io.github.classgraph.json.Id;

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
public class UserEntity {
    @Id
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String email;
    private String password;
    private String phone;
    private String role;
}

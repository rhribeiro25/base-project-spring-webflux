package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

/**
 * Class DTO User Create Request
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestPost {

    @NameConstraint(person = "Usuário(a)", field = "first")
    private String firstName;

    @NameConstraint(person = "Usuário(a)", field = "middle", require = false)
    private String middleName;

    @NameConstraint(person = "Usuário(a)", field = "last")
    private String lastName;

    @MotherNameConstraint(require = false)
    private String motherName;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    @PhoneConstraint
    private String phone;

    @RoleConstraint
    private String role;
}

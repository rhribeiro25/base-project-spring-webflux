package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO User Update Request
 *
 * @author Renan Henrique Ribeiro
 * @since 06/21/2021
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestPatch {

    @NameConstraint(person = "Usuário(a)", field = "first", require = false)
    private String firstName;

    @NameConstraint(person = "Usuário(a)", field = "middle", require = false)
    private String middleName;

    @NameConstraint(person = "Usuário(a)", field = "last", require = false)
    private String lastName;

    @MotherNameConstraint(require = false)
    private String motherName;

    @EmailConstraint(require = false)
    private String email;

    @PasswordConstraint(require = false)
    private String password;

    @PhoneConstraint(require = false)
    private String phone;

    @RoleConstraint(require = false)
    private String role;
}

package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Pattern;

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
public class UserUpdateRequest {

    private Long id;

    @NameConstraint(person = "Usuário(a)", field = "first")
    private String firstName;

    @OptionalNameConstraint(person = "Usuário(a)", field = "middle")
    private String middleName;

    @NameConstraint(person = "Usuário(a)", field = "last")
    private String lastName;

    @OptionalMotherNameConstraint
    private String motherName;

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

    @PhoneConstraint
    private String phone;

    @Pattern(regexp="^ADMIN|COORDINATOR|LEADER|INTERN$")
    private String role;
}

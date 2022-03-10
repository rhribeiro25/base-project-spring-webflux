package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.EmailConstraint;
import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.PasswordConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO User Login Request
 *
 * @author Renan Henrique Ribeiro
 * @since 03/09/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserRequestLogin {

    @EmailConstraint
    private String email;

    @PasswordConstraint
    private String password;

}

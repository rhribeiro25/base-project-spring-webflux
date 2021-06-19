package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constrain.NameConstraint;
import br.com.rhribeiro25.baseprojectspringwebflux.core.constrain.OptionalMotherNameConstraint;
import br.com.rhribeiro25.baseprojectspringwebflux.core.constrain.OptionalNameConstraint;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.relational.core.mapping.Table;

import java.time.LocalDateTime;

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
public class UserCreateRequest {
    @NameConstraint(person = "Usuário(a)", field = "first")
    private String firstName;

    @OptionalNameConstraint(person = "Usuário(a)", field = "middle")
    private String middleName;

    @NameConstraint(person = "Usuário(a)", field = "last")
    private String lastName;

    @OptionalMotherNameConstraint
    private String motherName;

    private String email;

    private String password;

    private String phone;

    private String role;
}

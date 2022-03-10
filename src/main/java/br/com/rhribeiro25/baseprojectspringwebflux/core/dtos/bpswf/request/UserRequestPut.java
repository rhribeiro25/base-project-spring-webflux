package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.request;

import br.com.rhribeiro25.baseprojectspringwebflux.core.constraints.*;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.fasterxml.jackson.datatype.jsr310.deser.LocalDateTimeDeserializer;
import com.fasterxml.jackson.datatype.jsr310.ser.LocalDateTimeSerializer;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

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
public class UserRequestPut {

    @NotNull
    private Long id;

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

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime createdAt;

    @JsonDeserialize(using = LocalDateTimeDeserializer.class)
    @JsonSerialize(using = LocalDateTimeSerializer.class)
    private LocalDateTime updatedAt;
}

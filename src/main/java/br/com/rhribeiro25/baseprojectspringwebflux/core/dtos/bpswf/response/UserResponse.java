package br.com.rhribeiro25.baseprojectspringwebflux.core.dtos.bpswf.response;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * Class DTO User Response
 *
 * @author Renan Henrique Ribeiro
 * @since 16/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserResponse {
    private Long id;
    private String firstName;
    private String middleName;
    private String lastName;
    private String motherName;
    private String email;
    private String phone;
    private String role;
}

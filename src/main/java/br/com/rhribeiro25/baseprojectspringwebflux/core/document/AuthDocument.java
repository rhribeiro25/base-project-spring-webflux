package br.com.rhribeiro25.baseprojectspringwebflux.core.document;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

/**
 * Class Black-List Document
 *
 * @author Renan Henrique Ribeiro
 * @since 18/02/2022
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Document("black_list")
public class AuthDocument implements GenericDocument {

    @Id
    private String id;

    private String token;
}

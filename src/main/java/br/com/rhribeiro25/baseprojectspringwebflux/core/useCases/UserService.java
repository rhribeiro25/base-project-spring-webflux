package br.com.rhribeiro25.baseprojectspringwebflux.core.useCases;

import br.com.rhribeiro25.baseprojectspringwebflux.dataprovider.database.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.log4j.Log4j2;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Mono;

/**
 * Class User Service
 *
 * @author Renan Henrique Ribeiro
 * @since 06/07/2021
 */
@Service
@Log4j2
@RequiredArgsConstructor
public class UserService {

    private final UserRepository userRepository;

    public Mono findById(Long id) {
        return userRepository.findById(id);
    }
}

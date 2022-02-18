package br.com.rhribeiro25.baseprojectspringwebflux.config.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

/**
 * Password Encoder Config class
 *
 * @author Renan Henrique Ribeiro
 * @since 08/02/2022
 */

@Configuration
public class PasswordEncoderConfig {

    @Bean
    public PasswordEncoder getPasswordEncoder(){
        return new BCryptPasswordEncoder();
    }
}

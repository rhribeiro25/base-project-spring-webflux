package br.com.rhribeiro25.baseprojectspringwebflux;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * Class main
 * @author Renan Henrique Ribeiro
 * @since 06/02/2021
 */
@SpringBootApplication
public class BaseProjectSpringWebfluxApplication {

	public static void main(String[] args) {
		SpringApplication.run(BaseProjectSpringWebfluxApplication.class, args);
		System.out.println("🚀 Server ready at http://localhost:9090/api");
	}

}

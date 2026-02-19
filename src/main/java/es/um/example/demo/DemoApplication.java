package es.um.example.demo;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.web.SecurityFilterChain;

@SpringBootApplication
public class DemoApplication {


	@Bean
	SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
    	http
			.csrf(csrf -> csrf.disable()) // Allow posts without CSRF token
        	.authorizeHttpRequests(auth -> auth
            	.requestMatchers("/api-docs/**",
                	"/swagger-ui.html",
                	"/swagger-ui/**",
					"/actuator/**"
            	).permitAll()
            	.anyRequest().authenticated()
        	).oauth2ResourceServer(oauth2 -> oauth2.jwt(Customizer.withDefaults()));
			
    	return http.build();
	}

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}

}

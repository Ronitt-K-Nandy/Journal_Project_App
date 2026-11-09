package com.newspaper.journal.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
public class NewConfiguration{

	private UserDetailServicesImpl userDetailsService;

	public NewConfiguration(UserDetailServicesImpl userDetailsService){
		this.userDetailsService=userDetailsService;
	}

	@Bean
    public BCryptPasswordEncoder getPasswordEncoder()
    {
    	return new BCryptPasswordEncoder();
    }

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) {
        http
                .csrf(csrf -> csrf.disable())
                .sessionManagement(session ->
						session.sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				)
                .authenticationProvider(authenticationProvider())
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/public/**").permitAll()
                        //.requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/journal/**", "/user/**").hasAnyRole("ADMIN","USER")
						.requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults());

        return http.build();
	}

	@Bean
	public DaoAuthenticationProvider authenticationProvider() {
		log.info("userDetailsService:----",userDetailsService.getClass().toString());
        log.info("userDetailsService:----",userDetailsService.getClass().toString());
		DaoAuthenticationProvider authProvider =new DaoAuthenticationProvider(userDetailsService);
        authProvider.setPasswordEncoder(getPasswordEncoder());
		return authProvider;
	}
}

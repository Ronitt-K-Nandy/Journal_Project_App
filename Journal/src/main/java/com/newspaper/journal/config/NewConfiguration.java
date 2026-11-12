package com.newspaper.journal.config;

import com.newspaper.journal.filter.JwtFilter;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import static org.springframework.security.config.Customizer.withDefaults;

@Slf4j
@Configuration
@EnableWebSecurity
public class NewConfiguration{

	private UserDetailServicesImpl userDetailsService;

	@Autowired
	private JwtFilter jwtFilter;

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
                        .requestMatchers("/public/**","/user/login").permitAll()
						//.requestMatchers("/journal/**", "/user/**").authenticated()
                        .requestMatchers("/journal/**", "/user/**").hasAnyRole("ADMIN","USER")
						.requestMatchers("/admin/**").hasRole("ADMIN")
                        .anyRequest().authenticated()
                )

                .httpBasic(withDefaults())
				.addFilterBefore(jwtFilter, UsernamePasswordAuthenticationFilter.class);

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

	@Bean
	public AuthenticationManager authenticationManager(AuthenticationConfiguration config) throws Exception {
		return config.getAuthenticationManager();
	}
}

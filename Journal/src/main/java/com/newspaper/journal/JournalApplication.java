package com.newspaper.journal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.web.client.RestTemplate;

@EnableWebSecurity
@SpringBootApplication//(@ComponentScan + @EnableAutoConfiguration + @Configuration)
public class JournalApplication {

	public static void main(String[] args) {
		SpringApplication.run(JournalApplication.class, args);
	}

	@Bean
	public RestTemplate createRestTemplateObject()
	{
		return new RestTemplate();
	}
}

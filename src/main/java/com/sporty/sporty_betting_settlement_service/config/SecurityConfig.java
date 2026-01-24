package com.sporty.sporty_betting_settlement_service.config;

import com.sporty.sporty_betting_settlement_service.entity.AppUser;
import com.sporty.sporty_betting_settlement_service.repositories.UserRepository;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

	@Bean
	public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
	    http
	        .authorizeHttpRequests(auth -> auth
	            .requestMatchers("/signup", "/css/**","/images/**", "/h2-console/**").permitAll()  // allow H2 console
	            .anyRequest().authenticated()
	        )
	        .formLogin(form -> form
	            .loginPage("/login")
	            .defaultSuccessUrl("/event-outcome", true)
	            .permitAll()
	        )
	        .logout(logout -> logout
	            .logoutSuccessUrl("/login?logout")
	            .permitAll()
	        )
	        .csrf(csrf -> csrf
	            .ignoringRequestMatchers("/h2-console/**")  // disable CSRF for H2
	        )
	        .headers(headers -> headers
	            .frameOptions(frame -> frame.sameOrigin())  // allow H2 frames
	        );

	    return http.build();
	}
	
    @Bean
    public UserDetailsService userDetailsService(UserRepository userRepository) {
    	System.out.println("Inside UserDetailsService ::");
        return email -> {
            AppUser user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new UsernameNotFoundException("User not found: " + email));
            System.out.println("User found: " + user.getEmail() + ", role: " + user.getRole());
            return org.springframework.security.core.userdetails.User.builder()
                    .username(user.getEmail())
                    .password(user.getPassword())
                    .roles(user.getRole().replace("ROLE_", ""))
                    .build();
        };
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}

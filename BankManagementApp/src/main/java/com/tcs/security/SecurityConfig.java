package com.tcs.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
public class SecurityConfig {

    private final JwtAuthenticationFilter jwtFilter;
    private final CustomAccessDeniedHandler accessDeniedHandler;

    public SecurityConfig(JwtAuthenticationFilter jwtFilter,CustomAccessDeniedHandler accessDeniedHandler) {
        this.jwtFilter = jwtFilter;
        this.accessDeniedHandler=accessDeniedHandler;
        
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http)
            throws Exception {

        http.csrf(csrf -> csrf.disable());

        http.authorizeHttpRequests(auth -> auth
                .requestMatchers(
                        "/auth/register",
                        "/auth/login",
                        "/swagger-ui/**",
                        "/v3/api-docs/**",
                        
                        "/swagger-ui-custom.html"
                ).permitAll()

                .requestMatchers("/super-admin/**")
                .hasRole("SUPER_ADMIN")

                .requestMatchers("/admin/**")
                .hasRole("ADMIN")

                .requestMatchers("/customer/**")
                .hasRole("CUSTOMER")
                
                .requestMatchers("/users")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                
                .requestMatchers("/**/block")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")

                .requestMatchers("/**/unblock")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                
                .requestMatchers("/accounts")
                .hasAnyRole("ADMIN", "CUSTOMER")
                
                
                .requestMatchers("/accounts/user/**")
                .hasAnyRole("ADMIN", "SUPER_ADMIN","CUSTOMER")
                
                .requestMatchers("/accounts/getById/**")
                .hasAnyRole("ADMIN", "SUPER_ADMIN","CUSTOMER")
                
                .requestMatchers("/transactions")
                .hasAnyRole("ADMIN", "SUPER_ADMIN","CUSTOMER")
                
                .requestMatchers("/loans")
                .hasAnyRole("ADMIN", "SUPER_ADMIN","CUSTOMER")
                
                .requestMatchers("/**/reject")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                
                .requestMatchers("/**/approve")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                
                .requestMatchers("/**/pending")
                .hasAnyRole("ADMIN", "SUPER_ADMIN")
                
                .requestMatchers("/loans/user/**")
                .hasAnyRole("ADMIN", "SUPER_ADMIN","CUSTOMER")
                
                
                .anyRequest().authenticated()
        );
        
        http.exceptionHandling(exceptionHandlingConfigurer -> 
        exceptionHandlingConfigurer.accessDeniedHandler(accessDeniedHandler)
    );

        http.sessionManagement(session ->
                session.sessionCreationPolicy(
                        SessionCreationPolicy.STATELESS
                )
        );

        http.addFilterBefore(
                jwtFilter,
                UsernamePasswordAuthenticationFilter.class
        );

        return http.build();
    }
}


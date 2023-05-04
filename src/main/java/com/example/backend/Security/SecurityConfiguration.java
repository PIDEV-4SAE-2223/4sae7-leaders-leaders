package com.example.backend.Security;

import com.example.backend.Entity.Formation;
import com.example.backend.Services.InFormationService;
import com.example.backend.dto.ResponseFormation;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;
import java.util.Set;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
@EnableGlobalMethodSecurity(prePostEnabled = true)

public class SecurityConfiguration {
    private final AuthenticationProvider authenticationProvider;
    private final JwtAuthentificationFilter JwtAuthFilter;
    private final LogoutHandler logoutHandler;


    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http
                .csrf()
                .disable()
                .authorizeRequests()
                .antMatchers("/imageDB/**","/images/**","/trainer-evaluation.html","/swagger-ui/**", "/auth/api/**","/save","/api/feedback/**","/api/formation/**", "/api/certificat/**", "/api/quizz/**", "/api/proposition/**", "/auth/api/register", "/Dashboard/**", "/roles", "/auth/api/change-password").permitAll()
                .antMatchers("/v3/api-docs/**", "/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
                        "/swagger-ui/**", "/analyse/**", "/types/**", "/rendezvous/**", "/restriction/**", "/report/**", "/application/**", "/equipement/**", "/intern/**", "/internship/**", "/leave/**", "/offer/**", "/shift/**").permitAll()
                .anyRequest()
                .authenticated()
                .and()
                .sessionManagement()
                .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(JwtAuthFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
                .logoutUrl("/auth/api/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
                .and().csrf().disable();
        http.csrf().disable();
        return http.build();


    }



}

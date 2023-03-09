package com.example.backend.Security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

import javax.servlet.Filter;

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
               .antMatchers("/swagger-ui/**").permitAll()
               .antMatchers("/v3/api-docs/**","/v2/api-docs", "/swagger-resources", "/swagger-resources/**",
                       "/swagger-ui/**","/analyse/**","/types/**","/rendezvous/**","/restriction/**","/report/**","/application/**","/equipement/**","/intern/**","/internship/**","/leave/**","/offer/**","/shift/**","/api/**").permitAll()
               .antMatchers("/swagger-ui/**").permitAll()
               .antMatchers("/swagger-ui/**").permitAll()
               .antMatchers("/auth/api/**").permitAll()
               .antMatchers("/test/**").hasAuthority("ROLE_USER")
              // .antMatchers("/test/**").hasRole("")
               .anyRequest()
               .authenticated()
               .and()
               .sessionManagement()
               .sessionCreationPolicy(SessionCreationPolicy.STATELESS)
               .and()
               .authenticationProvider(authenticationProvider)
           .addFilterBefore(JwtAuthFilter,UsernamePasswordAuthenticationFilter.class)
               .logout()
               .logoutUrl("/auth/api/logout")
               .addLogoutHandler(logoutHandler)
               .logoutSuccessHandler((request, response, authentication) -> SecurityContextHolder.clearContext())
       ;
       return http.build();


   }
}

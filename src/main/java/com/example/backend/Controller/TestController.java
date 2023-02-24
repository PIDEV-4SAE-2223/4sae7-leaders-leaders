package com.example.backend.Controller;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collection;

@RestController
@RequestMapping("/test")
public class TestController {

    @GetMapping("/")
    public String home(){
        return ("<h1>weclome</h1>");
    }
    @GetMapping("/onlyadmin")
    @PreAuthorize("hasAuthority('ROLE_ADMIN')")

    public String admin(){
        return ("<h1>weclome admin</h1>");
    }
    @GetMapping("/onlyuser")
    @PreAuthorize("hasAuthority('ROLE_USER')")
    public String user(){
        return ("<h1>weclome user</h1>");
    }
    @GetMapping("/onlymoderateur")
    public String moderateur(){
        return ("<h1>weclome moderateur</h1>");
    }

    @PreAuthorize("hasRole('ROLE_ADMIN')")
    @GetMapping("/admin")
    public String adminEndpoint() {
        return "Hello, admin!";
    }
    @GetMapping("/protected")
    public String protectedEndpoint(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        boolean isUser = authorities.contains(new SimpleGrantedAuthority("ROLE_USER"));

        if (isAdmin) {
            return "Hello, admin!";
        } else {
            return "Hello, user!";
        }
    }
}

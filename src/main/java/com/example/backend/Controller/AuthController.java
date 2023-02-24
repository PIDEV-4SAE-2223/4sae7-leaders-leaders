package com.example.backend.Controller;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.Token;
import com.example.backend.Entity.User;
import com.example.backend.Playload.Request.AuthentificationRequest;
import com.example.backend.Playload.Request.RegisterRequest;
import com.example.backend.Playload.Response.AuthentificationResponse;
import com.example.backend.Repository.TokenRepository;
import com.example.backend.Services.AuthenticationService;
import com.example.backend.Services.IUserService;
import com.example.backend.generic.GenericController;

import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import javax.swing.*;
import javax.validation.Valid;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthController extends GenericController<User,Long> {

    @Autowired
    AuthenticationManager authenticationManager;

    private IUserService iUserService ;

    private final AuthenticationService service;
    private TokenRepository tokenRepository;


    @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register (@RequestBody RegisterRequest request)
{
    System.out.println("fdsfdsfdsfdfdf" + request.getFirstName());

    return ResponseEntity.ok(service.register(request));

}

    @PostMapping ("/authenticate")
    public ResponseEntity<AuthentificationResponse> authenticate(
            @RequestBody AuthentificationRequest request
    ) throws Exception {

        return ResponseEntity.ok(service.authenticate(request));
    }

    @GetMapping("/protected")
    public String protectedEndpoint(Authentication authentication) {
        Collection<? extends GrantedAuthority> authorities = authentication.getAuthorities();
        boolean isAdmin = authorities.contains(new SimpleGrantedAuthority("ROLE_ADMIN"));
        if (isAdmin) {
            return "Hello, admin!";
        } else {
            return "Hello, user!";
        }
    }


















}

package com.example.backend.Controller;

import com.example.backend.Entity.Role;
import com.example.backend.Entity.User;
import com.example.backend.Playload.Request.AuthentificationRequest;
import com.example.backend.Playload.Request.RegisterRequest;
import com.example.backend.Playload.Response.AuthentificationResponse;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.TokenRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Security.JwtService;
import com.example.backend.Services.AuthenticationService;
import com.example.backend.Services.IUserService;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;
import java.util.Collections;

@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthController extends GenericController<User, Long> {

    private final AuthenticationService service;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JavaMailSender javaMailSender;
    private IUserService iUserService;
    private TokenRepository tokenRepository;
    private final PasswordEncoder passwordEncoder;


    @PostMapping("/register")
    public ResponseEntity<AuthentificationResponse> register(@RequestBody RegisterRequest request) {
        System.out.println("fdsfdsfdsfdfdf" + request.getFirstName());

        return ResponseEntity.ok(service.register(request));

    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthentificationRequest request) throws Exception {
        User user = userRepository.findByEmail(request.getEmail()).orElse(null);
        if (request == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }

        if (user.getPasswordneedschange()) {
            return ResponseEntity.status(HttpStatus.PAYMENT_REQUIRED).body("Password change required");
        }


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
    @PostMapping("/adduser")
    public ResponseEntity<User> createUser(@RequestBody User user) {
        Role role = roleRepository.findById(3).orElseThrow(() -> new RuntimeException("Role not found"));
        user.setRoles(Collections.singleton(role));
        User savedUser = userRepository.save(user);
        String message = "Welcome to our app! Your email is " + savedUser.getEmail() + " and your password is " + user.getPassword();
        SimpleMailMessage sm = new SimpleMailMessage();
        sm.setFrom("#########");
        sm.setTo(savedUser.getEmail());
        sm.setSubject("Welcome to Our app");
        sm.setText(message);
        javaMailSender.send(sm);
        return ResponseEntity.ok(savedUser);

    }

    @PostMapping("/change-password")
    public ResponseEntity<String> changePassword(@RequestParam("email") String email, @RequestParam("oldPassword") String oldPassword, @RequestParam("newPassword") String newPassword) {
        // Look up the user by username
        User user = userRepository.findByEmail(email).orElse(null);
        // If the user doesn't exist or the old password is incorrect, return an error response
        if (user == null ){
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        // Set the new password and mark the user as having changed their password
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordneedschange(false);
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }


}

package com.example.backend.Controller;

import com.example.backend.Entity.RoleEnum;
import com.example.backend.Entity.User;
import com.example.backend.Playload.Request.AuthentificationRequest;
import com.example.backend.Playload.Request.RegisterRequest;
import com.example.backend.Playload.Response.AuthentificationResponse;
import com.example.backend.Repository.RoleRepository;
import com.example.backend.Repository.UserRepository;
import com.example.backend.Services.AuthenticationService;
import com.example.backend.Validation.EmailExistsException;
import com.example.backend.Validation.InvalidPasswordException;
import com.example.backend.Validation.PasswordValidator;
import com.example.backend.generic.GenericController;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.LockedException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import javax.persistence.EntityNotFoundException;
import java.util.List;

@CrossOrigin(origins = "http://localhost:4200")
@RestController
@RequestMapping("/auth/api")
@RequiredArgsConstructor
public class AuthController extends GenericController<User, Long> {

    private final AuthenticationService service;
    private final RoleRepository roleRepository;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    JavaMailSender javaMailSender;
    @Autowired
    private PasswordValidator passwordValidator;


    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody RegisterRequest request) throws MessagingException, EmailExistsException {
        MimeMessage message = javaMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
        helper.setFrom("#########");
        helper.setTo(request.getEmail());
        helper.setSubject("Welcome to Our Laboratory");
        String htmlMsg = "<html><head><style>" + "body { background-color: #f1f1f1; font-family: Arial, sans-serif; }" + ".container { max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 10px; }" + "h1 { color: #007bff; font-size: 28px; }" + "p { font-size: 18px; line-height: 1.5; }" + "</style></head><body>" + "<div class='container'>" + "<h1>Welcome to HealthCare Management App!</h1>" + "<p>Hello " + request.getFirstName() + ",</p>" + "<p>Thank you for registering with us. We are thrilled to have you on board and would like to extend a warm welcome to you. Our team is dedicated to providing you with the best healthcare management services available, and we are committed to making your experience with us as pleasant as possible.</p>" + "<p>If you have any questions or concerns, please do not hesitate to contact us. We look forward to serving you!</p>" + "<p>Best regards,<br/>The HealthCare Management Team</p>" + "</div></body></html>";
        helper.setText(htmlMsg, true);
        javaMailSender.send(message);
        try {

            passwordValidator.validate(request.getPassword());

            return ResponseEntity.ok(service.register(request));

        } catch (InvalidPasswordException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid password");

        } catch (EmailExistsException e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(e.getMessage());
        }


    }

    @PostMapping("/authenticate")
    public ResponseEntity<?> authenticate(@RequestBody AuthentificationRequest request) throws InvalidPasswordException {

        try {
            return ResponseEntity.ok(service.authenticate(request));
        } catch (UsernameNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        } catch (javax.naming.AuthenticationException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Just change your password ");
        } catch (LockedException e) {
            return ResponseEntity.status(HttpStatus.LOCKED).body("Your account has been locked");
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while authenticating the user");
        }
    }

//    @GetMapping("/current")
//    public User getCurrentUser(Authentication authentication) {
//        if (authentication == null || !authentication.isAuthenticated()) {
//            throw new UsernameNotFoundException("User not found");
//        }
//        User userDetails = (User) authentication.getPrincipal();
//        return userRepository.findByEmail(userDetails.getUsername()).orElse(null);
//    }


    @PostMapping("/createNewUser/{roleEnum}")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> createUser(@RequestBody RegisterRequest request, @PathVariable RoleEnum roleEnum) throws EmailExistsException, MessagingException {
//        MimeMessage message = javaMailSender.createMimeMessage();
//        MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
//        helper.setFrom("#########");
//        helper.setTo(request.getEmail());
//        helper.setSubject("Welcome to Our Laboratory");
//
//        String htmlMsg = "<html><head><style>" + "body { background-color: #f1f1f1; font-family: Arial, sans-serif; }" + ".container { max-width: 600px; margin: auto; background-color: #fff; padding: 20px; border-radius: 10px; }" + "h1 { color: #007bff; font-size: 28px; }" + "p { font-size: 18px; line-height: 1.5; }" + "</style></head><body>" + "<div class='container'>" + "<h1>Welcome to HealthCare Management App!</h1>" + "<p>Hello " + request.getFirstName() + " Welcome to our app! Your email is " + request.getEmail() + " and your password is " + request.getPassword() + " ,</p>" + "<p>If you have any questions or concerns, please do not hesitate to contact us. We look forward to serving you!</p>" + "<p>Best regards,<br/>The HealthCare Management Team</p>" + "</div></body></html>";
//        helper.setText(htmlMsg, true);
//
//        javaMailSender.send(message);
        try {
            AuthentificationResponse user = service.createUser(request, roleEnum);

            return ResponseEntity.ok(user);

        } catch (EmailExistsException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Email deja existent");

        }


    }


    @PostMapping("/changepassword/{email}/{newPassword}")
    public ResponseEntity<String> changePassword(@RequestParam("email") String email, @RequestParam("newPassword") String newPassword) {
        User user = userRepository.findByEmail(email).orElse(null);
        if (user == null) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("Invalid username or password");
        }
        user.setPassword(passwordEncoder.encode(newPassword));
        user.setPasswordneedschange(false);
        userRepository.save(user);

        return ResponseEntity.ok("Password changed successfully");
    }


    @PutMapping("/users/{userId}/unblock")
    @PreAuthorize("hasRole('ROLE_ADMIN')")
    public ResponseEntity<?> unblockUserAccount(@PathVariable Long userId) {
        service.unblockAccount(userId);
        return ResponseEntity.ok().build();
    }

    @PutMapping("/{id}/edit")
    public ResponseEntity<?> editUserAccount(@PathVariable("id") Long userId, @RequestBody User updatedUser) {
        User user = userRepository.findById(userId).orElseThrow(() -> new EntityNotFoundException("User not found with ID " + userId));

        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setEmail(updatedUser.getEmail());
        user.setPassword(updatedUser.getPassword());

        userRepository.save(user);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/getUsers")
    public List<User> getAllInterns() {
        return userRepository.findAll();
    }

}

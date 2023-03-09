package com.example.backend.Entity;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
@Entity
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "idUser", nullable = false)
    @Setter(AccessLevel.NONE)
     private Long id;


    private String username;


    private String firstname;


    private String lastname;

    private String password;


    private String birthdate;
    private Sexe sexe;
    private int age;


@NotBlank
    private String adresse;
    @Email
    private String email;
    private Boolean passwordneedschange = true;
    private int failedLoginAttempts = 0;
    private boolean accountLocked = false;
    private LocalDateTime lastLockTime= LocalDateTime.now();


    @ManyToMany
    Set<Formation> formations_particip=new HashSet<>();
    @ManyToMany
    Set<Formation> formations_former=new HashSet<>();


    @Getter
    @Setter
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_roles",
            joinColumns = @JoinColumn(name = "user_id"),
            inverseJoinColumns = @JoinColumn(name = "roles_id"))
    private Set<Role> roles = new LinkedHashSet<>();

    @OneToMany(mappedBy = "user")
    private List<Token> tokens ;
    @JsonIgnore
    @ManyToMany(cascade = CascadeType.ALL,mappedBy = "users")
    Set<Shift> shifts=new HashSet<>();
    @JsonIgnore
    @OneToMany(cascade = CascadeType.ALL,mappedBy = "userr")
    Set<LeaveAuth> leaves = new HashSet<>();
    @JsonIgnore
    @OneToMany(mappedBy = "supplier")//many to many could be better
    Set<SupplierApplication> applications = new HashSet<>();

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        Set<GrantedAuthority> authorities = new HashSet<>();
        for (Role role : roles) {
            authorities.add(new SimpleGrantedAuthority(role.getRole().name()));
        }
        return authorities;
     }

//    @Override
//    public Collection<? extends GrantedAuthority> getAuthorities() {
//        List<SimpleGrantedAuthority> authorities = null;
//        for (Role role : roles) {
//            authorities = List.of(new SimpleGrantedAuthority(role.getRole().name()));
//        }
//        return authorities;
//    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }


}
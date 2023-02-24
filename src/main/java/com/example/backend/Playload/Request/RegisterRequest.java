package com.example.backend.Playload.Request;


import lombok.*;

import javax.validation.constraints.NotBlank;

//@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class RegisterRequest {
    @NotBlank(message = "username  is required")
    private String username;
    @NotBlank(message = "firstName  is required")
    private String firstName;
    @NotBlank(message = "lastName  is required")
    private String lastName;
    @NotBlank(message = "email  is required")
    private String email;
    @NotBlank(message = "password  is required")
    private String password;
    @NotBlank(message = "birthdate  is required")
    private String birthdate;
    @NotBlank(message = "adresse  is required")
    private String adresse;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getBirthdate() {
        return birthdate;
    }

    public void setBirthdate(String birthdate) {
        this.birthdate = birthdate;
    }

    public String getAdresse() {
        return adresse;
    }

    public void setAdresse(String adresse) {
        this.adresse = adresse;
    }
}

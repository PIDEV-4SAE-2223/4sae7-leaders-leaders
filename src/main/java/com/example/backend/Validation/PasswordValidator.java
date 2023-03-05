package com.example.backend.Validation;


import org.springframework.stereotype.Component;



@Component
public class PasswordValidator {


    private int minLength = 8;
    private int maxLength = 20;
    private boolean requireUpperCase = true;
    private boolean requireLowerCase = true;


    public boolean isValid(String password) {
        return password != null &&
                password.length() >= minLength &&
                password.length() <= maxLength &&
                (requireUpperCase && password.matches(".*[A-Z].*")) &&
                (requireLowerCase && password.matches(".*[a-z].*")) ;

    }

    public void setMinLength(int minLength) {
        this.minLength = minLength;
    }

    public void setMaxLength(int maxLength) {
        this.maxLength = maxLength;
    }

    public void setRequireUpperCase(boolean requireUpperCase) {
        this.requireUpperCase = requireUpperCase;
    }

    public void setRequireLowerCase(boolean requireLowerCase) {
        this.requireLowerCase = requireLowerCase;
    }

    public void validate(String password) throws InvalidPasswordException {
        if (!isValid(password)) {
            throw new InvalidPasswordException ("Invalid password");
        }
    }
}

package com.example.backend.Playload.Request;

import lombok.*;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class AuthentificationRequest {
    private String email;
     String password;

}

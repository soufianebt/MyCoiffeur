package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
public class LoginIdentifier {
    private String email;
    private String passWord;
}

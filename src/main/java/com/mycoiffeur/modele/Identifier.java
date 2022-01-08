package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@AllArgsConstructor
@Getter
@Setter
public class Identifier {
    private String email;
    private String passWord;
}

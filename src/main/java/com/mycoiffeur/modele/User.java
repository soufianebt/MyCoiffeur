package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passWord;
    private String address;
}

package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Document(value = "User")
public class User {
    @Id
    private String userId;
    private String firstName;
    private String lastName;
    private String email;
    private String passWord;
    private String address;
    private String userType;
}

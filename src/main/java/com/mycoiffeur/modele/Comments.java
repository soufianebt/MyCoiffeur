package com.mycoiffeur.modele;

import org.springframework.data.annotation.Id;

public class Comments {
    @Id
    private String commentId;
    private String comment;

}

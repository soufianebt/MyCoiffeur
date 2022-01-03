package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Reviews")
@AllArgsConstructor
public class Review {
    @Id
    private String reviewId;
    private String clientId;
    private String feedBack;
    private Integer note;

}

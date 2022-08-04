package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Reviews")
@AllArgsConstructor
@Data
public class Review {
    @Id
    private String reviewId;
    private String clientId;
    private String profileId;
    private String feedBack;
    private Integer note;

}

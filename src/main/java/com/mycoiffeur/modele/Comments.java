package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

@Document("Comments")
@Data
@NoArgsConstructor

public class Comments {
    @Id
    private String commentId;
    private String userId;
    private String postId;
    private String comment;


}

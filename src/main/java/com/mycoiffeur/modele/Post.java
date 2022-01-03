package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;
import java.util.List;
@Document("Post")

public class Post {
    @Id
    private String id;
    private Date dateOfPost;
    private List<Comments> listOfComment;
}

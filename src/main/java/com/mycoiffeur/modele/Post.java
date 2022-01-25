package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.ArrayList;
import java.util.Date;

@Document("Post")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class Post {
    @Id
    private String PostId;
    private Date dateOfPost;
    private String coiffureId;
    private String description;
    private String postType;
    private ArrayList<Comments> listOfComment;
    private String url;
}

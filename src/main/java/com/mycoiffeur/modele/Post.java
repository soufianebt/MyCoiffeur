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
    private String postId;
    private Date dateOfPost;
    private String profileId;
    private String description;
    private PostType postType;
    private String imageUrl;
}

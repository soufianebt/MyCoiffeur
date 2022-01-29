package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor

public class Profile {
    /**
     * the userId is the coiffureId*/
    @Id
    private String userId;


}

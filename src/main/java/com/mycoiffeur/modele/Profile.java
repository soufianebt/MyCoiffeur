package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.annotation.Id;

import java.util.List;


@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@ToString
public class Profile {
    /**
     * the userId is the coiffureId*/
    @Id
    private String userId;


}

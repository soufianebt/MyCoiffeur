package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@Document("Coiffeur")
public class Coiffure extends User {
    private Boolean compteIsVAlide;
    private Boolean isAvailable;


}

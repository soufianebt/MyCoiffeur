package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;

@AllArgsConstructor
@Document("Client")
public class Client {
    private Float X_label;
    private  Float Y_Label;

}

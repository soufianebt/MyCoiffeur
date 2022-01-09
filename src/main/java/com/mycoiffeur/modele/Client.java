package com.mycoiffeur.modele;

import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;


@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document("Client")
@Setter
@Getter
public class Client extends  User{
    private Float xLabel;
    private  Float yLabel;

    public Client(String userId, String firstName, String lastName, String email, String passWord, String address, String userType, Float xLabel, Float yLabel) {
        super(userId, firstName, lastName, email, passWord, address, userType);
        this.xLabel = xLabel;
        this.yLabel = yLabel;
    }
}

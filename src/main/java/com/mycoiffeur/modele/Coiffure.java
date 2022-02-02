package com.mycoiffeur.modele;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;



@Data
@ToString(callSuper = true)
@Document("Coiffure")
public class Coiffure extends User {
    private Boolean compteIsVAlide;
    private Boolean isAvailable;
    private String tele;
    private String imageUrl;
    private String waitTime;

    public Coiffure(String userId, String firstName, String lastName, String email, String passWord, String address, UserType userType, Boolean compteIsVAlide, Boolean isAvailable,
                    String tele, String imageUrl, String waitTime) {
        super(userId, firstName, lastName, email, passWord, address, userType);
        this.compteIsVAlide = compteIsVAlide;
        this.isAvailable = isAvailable;
        this.tele = tele;
        this.imageUrl = imageUrl;
        this.waitTime  = waitTime;
    }


}

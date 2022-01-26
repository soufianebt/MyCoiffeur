package com.mycoiffeur.modele;


import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@EqualsAndHashCode(callSuper = true)
@ToString(callSuper = true)
@Document("Coiffure")
public class Coiffure extends User {
    private Boolean compteIsVAlide;
    private Boolean isAvailable;

    public Coiffure(String userId, String firstName, String lastName, String email, String passWord, String address, UserType userType, Boolean compteIsVAlide, Boolean isAvailable) {
        super(userId, firstName, lastName, email, passWord, address, userType);
        this.compteIsVAlide = compteIsVAlide;
        this.isAvailable = isAvailable;
    }


}

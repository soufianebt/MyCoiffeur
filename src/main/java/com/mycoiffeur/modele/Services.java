package com.mycoiffeur.modele;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.number.money.MonetaryAmountFormatter;

import java.util.Date;
@Data
@AllArgsConstructor
@Getter
@Setter
public class Services {
    @Id
    private String serviceId;
    private String coiffureId;
    private String name;
    private String description;
    private Float price;

    //TODO : Change delay to time
    private Date delay;

}

package com.mycoiffeur.modele;

import org.springframework.data.annotation.Id;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.format.number.money.MonetaryAmountFormatter;

public class Services {
    @Id
    private String id;
    private String name;
    private String description;
    private Float price;

    @DateTimeFormat
    private String delay;

}

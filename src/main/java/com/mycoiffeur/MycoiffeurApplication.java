package com.mycoiffeur;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.Review;
import com.mycoiffeur.repository.CoiffeurRepo;
import com.mycoiffeur.repository.ReviewRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class MycoiffeurApplication {

    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
    }


}

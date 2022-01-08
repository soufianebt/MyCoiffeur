package com.mycoiffeur;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.modele.User;
import com.mycoiffeur.repository.ClientRepo;
import com.mycoiffeur.repository.CoiffeurRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class MycoiffeurApplication {
    @Autowired
    private CoiffeurRepo coiffeurRepo ;

    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(){
        return args ->{
            Coiffure coiffure = new Coiffure("DFDLKFLK",
                    "LKDLFKDLKF",
                    "FJKFJFDKJF",
                    "DLJFDKFJDKJFDKJ",
                    "KJDKFJDFKJ",
                    "DFJKDFJDKJF",
                    "LJFDJFKDJFKJD",
                    true,
                    false);



            coiffeurRepo.save(coiffure);
            Optional<Coiffure> coiffure1 = coiffeurRepo.findByEmail("DLJFDKFJDKJFDKJ");
            System.out.println("---- getting clientgfgf----"+"\n"
            +coiffure1.get().toString());
        };
    }

}

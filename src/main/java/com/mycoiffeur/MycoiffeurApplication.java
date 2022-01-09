package com.mycoiffeur;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.repository.CoiffureRepo;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
public class MycoiffeurApplication {
    @Autowired
    private CoiffureRepo coiffureRepo ;


    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
//        Logger logger =  LoggerFactory.getLogger(MycoiffeurApplication.class);
//        logger.info("Hello World");
    }

    @Bean
    CommandLineRunner runner(){
        return args ->{
//            Coiffure coiffure = new Coiffure("DFDLKFLK",
//                    "LKDLFKDLKF",
//                    "FJKFJFDKJF",
//                    "DLJFDKFJDKJFDKJ",
//                    "KJDKFJDFKJ",
//                    "DFJKDFJDKJF",
//                    "LJFDJFKDJFKJD",
//                    true,
//                    false);
//
//
//
//            coiffeurRepo.save(coiffure);
            Iterable<String> iterable
                    = Arrays.asList("2", "2");
            Iterable<Coiffure> coiffure1 = coiffureRepo.findAllByEmail(iterable);
           coiffure1.forEach(coiffure ->  System.out.println("---- getting clientgfgf----"+"\n"
                   +coiffure.toString()));
        };
    }

}

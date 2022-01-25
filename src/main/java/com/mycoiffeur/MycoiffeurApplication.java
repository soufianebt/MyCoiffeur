package com.mycoiffeur;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.storage.StorageProperties;
import com.mycoiffeur.storage.StorageService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Arrays;
import java.util.Iterator;
import java.util.Optional;

@SpringBootApplication
@EnableMongoRepositories
@EnableSwagger2
@EnableConfigurationProperties(StorageProperties.class)
public class MycoiffeurApplication {
    @Autowired
    private CoiffureRepo coiffureRepo ;


    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
//        Logger logger =  LoggerFactory.getLogger(MycoiffeurApplication.class);
//        logger.info("Hello World");
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            storageService.deleteAll();
            storageService.init();
        };
    }

}

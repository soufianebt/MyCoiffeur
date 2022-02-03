package com.mycoiffeur;

import com.mycoiffeur.controllers.ClientController;
import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.repository.CoiffureRepo;
import com.mycoiffeur.storage.StorageProperties;
import com.mycoiffeur.storage.StorageService;
import lombok.AllArgsConstructor;
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
@AllArgsConstructor
public class MycoiffeurApplication {
    private final CoiffureRepo coiffureRepo ;
    private final Logger logger = LoggerFactory.getLogger(MycoiffeurApplication.class);

    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
    }

    @Bean
    CommandLineRunner init(StorageService storageService) {
        return (args) -> {
            try {
                storageService.deleteAll();
                storageService.init();
                logger.info("init script Of Python Installation");
                ProcessBuilder builder = new ProcessBuilder("python", "-m pip install pymongo");
                Process process = builder.start();
                ProcessBuilder builder2 = new ProcessBuilder("python", "-m pip install dnspython");
                Process process2 = builder2.start();
            }catch (Exception e){
                logger.error(e.toString());
            }
        };
    }

}

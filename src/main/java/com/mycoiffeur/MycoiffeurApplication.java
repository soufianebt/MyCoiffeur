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

@SpringBootApplication
//@EnableMongoRepositories
public class MycoiffeurApplication {
    @Autowired
    private CoiffeurRepo coiffeur;
    @Autowired
    private ReviewRepo reviewRepo;
    public static void main(String[] args) {
        SpringApplication.run(MycoiffeurApplication.class, args);
    }

    @Bean
    CommandLineRunner runner(){
        return args -> {
            Coiffure user = new Coiffure();
            Review review = new Review("fsdfdf", "fdeff", "fdfdsf", 5);
            user.setEmail("soufiane55@gmail.com");
            user.setFirstName("soufiane");
            user.setLastName("boutahiri");
            user.setPassWord("password" );
            user.setAddress("skhorate temara");
            user.setIsAvailable(true);
            coiffeur.save(user);
            reviewRepo.save(review);
        };
    }
}

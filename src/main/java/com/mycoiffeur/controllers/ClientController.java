package com.mycoiffeur.controllers;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.services.ClientService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
/**
 * @author Soufiane Boutahiti
 *
 * */
@RestController
@AllArgsConstructor
public class ClientController {
    private final ClientService clientService;
    private final Logger logger = LoggerFactory.getLogger(ClientController.class);
    @GetMapping(value ="/client/{clientId}")
    public ResponseEntity<Client> getRecommendation(@PathVariable String clientId){
        try {
            Optional<Client> client = clientService.getClient(clientId);
            if(client.isPresent()){
                Client client1 = client.get();
                client1.setPassWord("");
                logger.info("Client is present");
                return new ResponseEntity<>(client1, HttpStatus.OK);
            }else {
                logger.info("Client is not present");
                return new ResponseEntity<>( HttpStatus.NOT_FOUND);
            }
        } catch(Exception e){
            logger.error(e.toString());
            return new ResponseEntity<>(HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
}

package com.mycoiffeur.services;

import com.mycoiffeur.modele.Client;
import com.mycoiffeur.repository.ClientRepo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;
@Service
@AllArgsConstructor
public class ClientServiceImp implements ClientService {
    private ClientRepo clientRepo;

    @Override
    public Optional<Client> getClient(String clientId) {
        return   this.clientRepo.findById(clientId);
    }
}

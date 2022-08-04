package com.mycoiffeur.services;

import com.mycoiffeur.modele.Client;

import java.util.Optional;

public interface ClientService {
    Optional<Client> getClient(String clientId);
}

package com.mycoiffeur.services;

import com.mycoiffeur.modele.Client;

import java.util.Optional;

public interface ClinetService {
    Optional<Client> getClient(String clientId);
}

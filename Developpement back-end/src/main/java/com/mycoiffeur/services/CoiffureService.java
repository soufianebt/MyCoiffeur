package com.mycoiffeur.services;

import com.mycoiffeur.modele.Coiffure;

import java.util.Optional;

public interface CoiffureService {
    Iterable<Coiffure> getCoiffures();
    Optional<Coiffure> getCoiffeureById(String coiffeurId);
    void editeCoiffeur(Coiffure coiffure);

}

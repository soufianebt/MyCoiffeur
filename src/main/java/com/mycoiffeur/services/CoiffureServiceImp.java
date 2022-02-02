package com.mycoiffeur.services;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.repository.CoiffureRepo;
import lombok.AllArgsConstructor;

import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class CoiffureServiceImp implements CoiffureService{
    private CoiffureRepo coiffureRepo;
    @Override
    public Iterable<Coiffure> getCoiffures() {
        Iterable<Coiffure> coiffures = coiffureRepo.findAll();
        coiffures.forEach(coiffure -> {
            coiffure.setPassWord("");
        });
        return coiffures;
    }

    public Optional<Coiffure> getCoiffeureById(String coiffeurId) {
        return this.coiffureRepo.findById(coiffeurId);
    }
}

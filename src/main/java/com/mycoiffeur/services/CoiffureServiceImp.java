package com.mycoiffeur.services;

import com.mycoiffeur.modele.Coiffure;
import com.mycoiffeur.repository.CoiffureRepo;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Required;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class CoiffureServiceImp implements CoiffureService{
    private CoiffureRepo coiffureRepo;
    @Override
    public Iterable<Coiffure> getCoiffures() {
        Iterable<Coiffure> coiffures = coiffureRepo.findAll();
        coiffures.forEach(coiffure -> {
            coiffure.setPassWord("");
            coiffure.setEmail("");
        });
        return coiffures;
    }
}

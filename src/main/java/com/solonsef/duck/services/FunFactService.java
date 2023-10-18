package com.solonsef.duck.services;

import com.solonsef.duck.entities.FunFact;

import java.util.List;

public interface FunFactService {

//    void save(FunFact funFact);

//    @Transactional
//    void save(FunFact funFact, String username);


    void save(FunFact funFact);

    FunFact findById(int id);

    FunFact findRandom();

    List<FunFact> findAll();

    int getQueueCount();


}

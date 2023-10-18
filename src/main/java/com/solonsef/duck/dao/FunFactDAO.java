package com.solonsef.duck.dao;

import com.solonsef.duck.entities.FunFact;

import java.util.List;
import java.util.Optional;

public interface FunFactDAO {

    void save(FunFact funFact);

    List<FunFact> findAll();

    Optional<FunFact> findById(int id);

    FunFact findRandom();


    int getQueueCount();

}

package com.solonsef.duck.dao;

import com.solonsef.duck.entities.FunFact;

import java.util.List;
import java.util.Optional;

public interface FunFactDAO {

    public void save(FunFact funFact);

    public List<FunFact> findAll();

    public Optional<FunFact> findById(int id);

    FunFact findRandom();
}

package com.solonsef.duck.services;

import com.solonsef.duck.dao.FunFactDAO;
import com.solonsef.duck.entities.FunFact;
import com.solonsef.duck.exceptions.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service

public class FunFactServiceImpl implements FunFactService {
    private final FunFactDAO funFactDAO;

    public FunFactServiceImpl(FunFactDAO funFactDAO) {
        this.funFactDAO = funFactDAO;
    }

    @Override
    @Transactional
    public void save(FunFact funFact) {
        funFactDAO.save(funFact);
    }

    @Override
    public FunFact findById(int id) {
        Optional<FunFact> funFact = funFactDAO.findById(id);
        if (funFact.isEmpty()) {
            throw new EntityNotFoundException(String.format("Could not find fun fact with id %d", id));
        }
        return funFact.get();
    }

    @Override
    public FunFact findRandom() {
        return funFactDAO.findRandom();
    }

    @Override
    public List<FunFact> findAll() {
        return funFactDAO.findAll();
    }

    @Override
    public int getQueueCount() {
        return funFactDAO.getQueueCount();
    }
}

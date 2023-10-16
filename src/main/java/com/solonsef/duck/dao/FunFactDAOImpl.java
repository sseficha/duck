package com.solonsef.duck.dao;

import com.solonsef.duck.entities.FunFact;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public class FunFactDAOImpl implements FunFactDAO {
    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public void save(FunFact funFact) {
        entityManager.persist(funFact);
    }

    @Override
    public List<FunFact> findAll() {
        TypedQuery<FunFact> query = entityManager.createQuery("FROM FunFact", FunFact.class);
        return query.getResultList();

    }

    @Override
    public Optional<FunFact> findById(int id) {
        FunFact funFact = entityManager.find(FunFact.class, id);
        return Optional.ofNullable(funFact);
    }

    public FunFact findRandom() {
        TypedQuery<FunFact> query = entityManager.createQuery(
                "FROM FunFact " +
                        "WHERE enabled = true  " +
                        "ORDER BY RAND() " +
                        "LIMIT 1", FunFact.class
        );
        return query.getSingleResult();
    }
}

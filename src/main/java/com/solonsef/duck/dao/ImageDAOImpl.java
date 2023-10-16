package com.solonsef.duck.dao;

import com.solonsef.duck.entities.Image;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.Query;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class ImageDAOImpl implements ImageDAO {

    @PersistenceContext
    private EntityManager entityManager;

    @Override
    public Image findRandom() {
        TypedQuery<Image> query = entityManager.createQuery(
                "FROM Image " +
                        "WHERE enabled = true " +
                        "ORDER BY RAND() " +
                        "LIMIT 1", Image.class
        );
        return query.getSingleResult();
    }

    @Override
    public Optional<Image> findById(int id) {
        Image image = entityManager.find(Image.class, id);
        return Optional.ofNullable(image);

    }


    @Override
    public void save(Image image) {
        entityManager.persist(image);
    }

    public int getLastId() {
        Query query = entityManager.createQuery(
                "SELECT IFNULL(MAX(id),1) FROM Image"
        );
        return (Integer) query.getSingleResult();
    }
}

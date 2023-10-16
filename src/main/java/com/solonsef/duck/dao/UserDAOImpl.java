package com.solonsef.duck.dao;

import com.solonsef.duck.entities.Role;
import com.solonsef.duck.entities.User;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public class UserDAOImpl implements UserDAO {

    @PersistenceContext
    EntityManager entityManager;

    @Override
    public Optional<User> findByUserName(String username) {
        TypedQuery<User> query = entityManager.createQuery("from User where username=:uName", User.class);
        query.setParameter("uName", username);
        User user;
        try {
            user = query.getSingleResult();
        } catch (Exception e) {
            user = null;
        }
        return Optional.ofNullable(user);
    }

    public void save(User user) {
        user.addRole(new Role("basic")); //default role for every user
        entityManager.persist(user);
    }
}

package com.solonsef.duck.dao;

import com.solonsef.duck.entities.User;

import java.util.Optional;

public interface UserDAO {
    Optional<User> findByUserName(String userName);

    public void save(User user);


}

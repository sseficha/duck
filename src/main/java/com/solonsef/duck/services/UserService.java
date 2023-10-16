package com.solonsef.duck.services;

import com.solonsef.duck.entities.User;
import org.springframework.security.core.userdetails.UserDetailsService;

import java.util.Optional;

public interface UserService extends UserDetailsService {

    public Optional<User> findByUserName(String username);

    public void save(User user);

}

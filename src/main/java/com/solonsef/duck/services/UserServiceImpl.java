package com.solonsef.duck.services;

import com.solonsef.duck.dao.UserDAO;
import com.solonsef.duck.entities.Role;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.exceptions.UsernameTakenException;
import jakarta.transaction.Transactional;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserServiceImpl implements UserService {
    private UserDAO userDAO;

    public UserServiceImpl(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    public Optional<User> findByUserName(String username) {
        return userDAO.findByUserName(username);
    }

    @Override
    @Transactional
    public void save(User user) {
        Optional<User> optionalUser = userDAO.findByUserName(user.getUsername());
        if (optionalUser.isEmpty()) {
            user.encryptPassword();
            userDAO.save(user);
        } else {
            throw new UsernameTakenException("Username taken");
        }
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<User> user = findByUserName(username);
        if (user.isEmpty()) {
            throw new UsernameNotFoundException("Invalid username or password");
        }
        User validUser = user.get();
        return new org.springframework.security.core.userdetails.User(
                validUser.getUsername(),
                validUser.getPassword(),
                mapRolesToAuthorities(validUser.getRoles()));
    }

    private Collection<? extends GrantedAuthority> mapRolesToAuthorities(Collection<Role> roles) {
        return roles.stream().map(role -> new SimpleGrantedAuthority(role.getName())).collect(Collectors.toList());
    }
}

package com.solonsef.duck.rest;

import com.solonsef.duck.dao.UserDAO;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserController {
    private UserDAO userDAO;

    public UserController(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

//    @GetMapping("/{username}")
//    public void getUserByUsername(@PathVariable String username) {
//        Optional<User> optUser = userDAO.findByUserName(username);
//        User user = optUser.get();
//        System.out.println(user);
//        System.out.println(user.getFunFacts()); //this is not raising LazyInitializationException
//    }

}

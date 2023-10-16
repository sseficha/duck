package com.solonsef.duck.rest;


import com.solonsef.duck.dto.UserCreationDTO;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.exceptions.UsernameTakenException;
import com.solonsef.duck.services.UserService;
import jakarta.validation.Valid;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;

import java.net.URL;


@Controller
public class MvcController {

    private ModelMapper modelMapper;
    private UserService userService;

    private ImageController imageController;

    public MvcController(UserService userService, ImageController imageController, ModelMapper modelMapper) {
        this.modelMapper = modelMapper;
        this.userService = userService;
        this.imageController = imageController;

    }

    @GetMapping("/")
    public String getHome(Model model) {
        URL imageUrl = imageController.getRandomImage();
        model.addAttribute("imageUrl", imageUrl);
        return "home";
    }

    @GetMapping("/swagger")
    public String getSwagger() {
        return "swagger";
//        return "redirect:swagger-ui/index.html";
    }

    @GetMapping("/show-signup")
    public String showSignup(Model model) {
        model.addAttribute("user", new UserCreationDTO());
        return "signup";
    }

    @GetMapping("/show-signup-success")
    public String showSignupSuccess() {
        return "signup-success";
    }

    @PostMapping("/process-signup")
    public String processSignup(@Valid @ModelAttribute("user") UserCreationDTO userCreationDTO, BindingResult theBindingResult) {
        User user = fromDTO(userCreationDTO);
        if (!theBindingResult.hasErrors()) {
            try {
                userService.save(user);
                return "redirect:show-signup-success"; //PRG pattern
            } catch (UsernameTakenException exc) {
                theBindingResult.rejectValue("username", "error.username", exc.getMessage());
            }
        }
        return "signup";
    }

    public User fromDTO(UserCreationDTO userCreationDTO) {
        return modelMapper.map(userCreationDTO, User.class);
    }
}

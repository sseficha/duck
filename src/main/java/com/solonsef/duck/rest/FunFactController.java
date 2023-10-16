package com.solonsef.duck.rest;


import com.solonsef.duck.dto.FunFactCreationDTO;
import com.solonsef.duck.dto.FunFactDTO;
import com.solonsef.duck.entities.FunFact;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.services.FunFactService;
import com.solonsef.duck.services.UserService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@RestController
@RequestMapping("/fun-facts")
@Validated
public class FunFactController {
    private FunFactService funFactService;
    private UserService userService;
    private ModelMapper modelMapper;

    @Autowired
    public FunFactController(FunFactService funFactService,
                             UserService userService,
                             ModelMapper modelMapper) {
        this.funFactService = funFactService;
        this.userService = userService;
        this.modelMapper = modelMapper;
    }

    @GetMapping("/{id}")
    public FunFactDTO getFunFact(@PathVariable @Min(1) int id) {
        FunFact funFact = funFactService.findById(id);
        return toDTO(funFact);
    }

//    @GetMapping()
//    public List<FunFactDTO> getFunFacts() {
//
//        List<FunFact> funFacts = funFactService.findAll();
//        return funFacts.stream().map(this::toDTO).toList();
//    }

    @GetMapping("/random")
    public FunFactDTO getFunFactRandom() {
        FunFact funFact = funFactService.findRandom();
        return toDTO(funFact);
    }

    @PostMapping
    public void saveFunFact(@RequestBody @Valid FunFactCreationDTO funFactDTO, Principal principal) {
        String username = principal.getName();
        User user = userService.findByUserName(username).get();
        FunFact funFact = fromDTO(funFactDTO);
        funFact.setUser(user);
        funFactService.save(funFact);
    }

    public FunFactDTO toDTO(FunFact funFact) {
        return modelMapper.map(funFact, FunFactDTO.class);
    }

    public FunFact fromDTO(FunFactCreationDTO funFactDTO) {
        return modelMapper.map(funFactDTO, FunFact.class);
    }


}
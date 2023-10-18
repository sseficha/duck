package com.solonsef.duck.rest;


import com.solonsef.duck.dto.CountDTO;
import com.solonsef.duck.dto.FunFactCreationDTO;
import com.solonsef.duck.dto.FunFactDTO;
import com.solonsef.duck.entities.FunFact;
import com.solonsef.duck.entities.User;
import com.solonsef.duck.services.FunFactService;
import com.solonsef.duck.services.UserService;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import jakarta.validation.constraints.Min;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;


@Tag(name = "Fun Facts")
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

    @GetMapping(value = "/{id}", produces = {"application/json"})
    public FunFactDTO getFunFact(@PathVariable @Min(1) int id) {
        FunFact funFact = funFactService.findById(id);
        return toDTO(funFact);
    }

    @GetMapping(value = "/random", produces = {"application/json"})
    public FunFactDTO getFunFactRandom() {
        FunFact funFact = funFactService.findRandom();
        return toDTO(funFact);
    }

    @GetMapping(value = "/queue", produces = {"application/json"})
    public CountDTO getQueueCount() {

        CountDTO countDTO = new CountDTO();
        countDTO.setCount(funFactService.getQueueCount());
        return countDTO;
    }

    @SecurityRequirement(name = "basic")
    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
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
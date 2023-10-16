package com.solonsef.duck.dto;

import jakarta.validation.constraints.NotBlank;

public class FunFactCreationDTO {
    @NotBlank(message = "Name is mandatory")
    private String fact;

    public String getFact() {
        return fact;
    }

    public void setFact(String fact) {
        this.fact = fact;
    }
}

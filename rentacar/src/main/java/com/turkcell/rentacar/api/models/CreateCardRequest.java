package com.turkcell.rentacar.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCardRequest(
    @NotNull @NotBlank @Size(min = 3, max = 35) String cardHolder,
    @NotNull @NotBlank String cardNo,
    @NotNull @Size(min = 3, max = 3) int CVV,
    @NotNull @Size(min = 1, max = 2) int expirationMonth,
    @NotNull @Size(min = 2, max = 2) int exporationYear) {}

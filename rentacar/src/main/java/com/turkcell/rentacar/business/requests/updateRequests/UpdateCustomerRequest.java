package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCustomerRequest(
    @NotNull @Positive int customerId,
    @NotNull @NotBlank @Email String email,
    @NotNull @NotBlank @Size(min = 3, max = 15) String password) {}

package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCorporateCustomerRequest(
    @NotNull @Positive int customerId,
    @NotNull @NotBlank @Size(min = 3, max = 32) @Email String email,
    @NotNull @NotBlank @Size(min = 3, max = 32) String password,
    @NotNull @NotBlank @Size(min = 3, max = 32) String companyName,
    @NotNull @NotBlank @Size(min = 3, max = 32) String taxNumber) {}

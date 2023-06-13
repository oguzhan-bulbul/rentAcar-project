package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateCorporateCustomerRequest(
    @NotNull @NotBlank @Email String email,
    @NotNull @NotBlank @Size(min = 4, max = 16) String password,
    @NotNull @NotBlank @Size(min = 2, max = 15) String companyName,
    @NotNull @NotBlank @Size(min = 2, max = 15) String taxNumber) {}

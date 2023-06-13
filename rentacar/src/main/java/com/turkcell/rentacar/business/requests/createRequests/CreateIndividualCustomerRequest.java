package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateIndividualCustomerRequest(
    @NotNull @NotBlank @Email String email,
    @NotNull @NotBlank @Size(min = 4, max = 16) String password,
    @NotNull @NotBlank @Size(min = 4, max = 16) String firstName,
    @NotNull @NotBlank @Size(min = 4, max = 16) String lastName,
    @NotNull @NotBlank @Size(min = 4, max = 16) String nationalIdentity) {}

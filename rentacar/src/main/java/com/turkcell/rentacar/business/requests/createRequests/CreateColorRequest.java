package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

public record CreateColorRequest(@NotBlank @NotNull @Size(min = 2, max = 15) String colorName) {}

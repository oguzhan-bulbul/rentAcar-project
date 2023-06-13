package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateColorRequest(
    @NotNull @Positive int colorId, @NotBlank @NotNull @Size(min = 2, max = 15) String colorName) {}

package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCityRequest(
    @NotNull @Positive int cityId, @NotNull @NotBlank @Size(min = 3, max = 35) String cityName) {}

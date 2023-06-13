package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateCarRequest(
    @NotNull @Positive int brandId,
    @NotNull @Positive int colorId,
    @NotNull @Max(2022) @Min(2000) int carModelYear,
    @NotNull @Max(3000) @Min(50) double carDailyPrice,
    @NotNull @Positive int currentKm,
    @NotBlank @NotNull @Size(min = 2, max = 200) String description) {}

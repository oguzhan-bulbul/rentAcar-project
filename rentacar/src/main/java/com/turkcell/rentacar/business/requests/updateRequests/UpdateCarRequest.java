package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import jakarta.validation.constraints.Size;

public record UpdateCarRequest(
    @NotNull @Positive int carId,
    @NotNull @Max(3000) @Min(50) double carDailyPrice,
    @NotNull @Max(2022) @Min(2000) int carModelYear,
    @NotNull @PositiveOrZero int currentKm,
    @NotBlank @NotNull @Size(min = 2, max = 200) String description,
    @NotNull @Positive int brandId,
    @NotNull @Positive int colorId) {}

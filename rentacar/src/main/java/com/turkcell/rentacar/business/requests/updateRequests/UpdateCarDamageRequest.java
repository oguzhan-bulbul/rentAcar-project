package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateCarDamageRequest(
    @NotNull @Positive int carDamageId,
    @NotNull @Positive int carId,
    @NotNull @NotBlank @Size(min = 2, max = 32) String damageRecord) {}

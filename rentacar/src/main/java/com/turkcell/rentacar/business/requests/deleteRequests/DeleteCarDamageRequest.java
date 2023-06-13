package com.turkcell.rentacar.business.requests.deleteRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record DeleteCarDamageRequest(@NotNull @Positive int carDamageId) {}

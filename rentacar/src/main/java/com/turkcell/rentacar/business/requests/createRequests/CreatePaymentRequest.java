package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record CreatePaymentRequest(@NotNull @Positive int rentId) {}

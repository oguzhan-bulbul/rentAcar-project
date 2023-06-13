package com.turkcell.rentacar.business.requests.endRequest;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record EndRentRequest(
    @NotNull @Positive int rentId,
    @NotNull @Positive int returnedKm,
    @NotNull @FutureOrPresent LocalDate returnDate) {}

package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record CreateCarMaintenanceRequest(
    @NotNull @NotBlank @Size(min = 2, max = 200) String description,
    @FutureOrPresent LocalDate returnDate,
    @NotNull @Positive int carId) {}

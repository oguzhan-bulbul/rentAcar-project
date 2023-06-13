package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;

public record UpdateCarMaintenanceRequest(
    @NotNull @Positive int maintenanceId,
    @NotBlank @NotNull @Size(min = 2, max = 200) String description,
    @NotNull @Positive int carId,
    LocalDate returnDate) {}

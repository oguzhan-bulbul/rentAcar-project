package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.Max;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import java.util.List;

public record CreateRentForIndividualRequest(
    @NotNull @Positive @Min(1) @Max(81) int rentedCityId,
    @NotNull @Positive @Min(1) @Max(81) int deliveredCityId,
    @NotNull @Positive int individualCustomerId,
    @NotNull LocalDate startDate,
    LocalDate finishDate,
    List<Integer> additionalServices,
    @NotNull @Positive int carId) {}

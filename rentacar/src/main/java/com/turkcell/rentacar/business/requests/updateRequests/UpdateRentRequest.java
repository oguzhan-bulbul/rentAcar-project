package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import java.time.LocalDate;
import java.util.List;

public record UpdateRentRequest(
    @NotNull @Positive int rentId,
    @NotNull @NotBlank @Size(min = 2, max = 25) int rentedCityId,
    @NotNull @NotBlank @Size(min = 2, max = 25) int deliveredCityId,
    List<Integer> additionalServices,
    @NotNull @Positive int customerId,
    @NotNull LocalDate startDate,
    LocalDate finishDate,
    @NotNull @Positive int carId) {}

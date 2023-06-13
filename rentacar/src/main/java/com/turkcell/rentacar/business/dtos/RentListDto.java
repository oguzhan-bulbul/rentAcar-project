package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

public record RentListDto(
    int rentId,
    String rentedCity,
    String deliveredCity,
    int startedKm,
    int returnedKm,
    LocalDate startDate,
    LocalDate finishDate,
    double bill,
    int carId,
    int orderedAdditionalServiceId) {}

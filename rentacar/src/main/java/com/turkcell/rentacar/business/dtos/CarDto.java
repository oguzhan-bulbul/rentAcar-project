package com.turkcell.rentacar.business.dtos;

public record CarDto(
    String brandName,
    String colorName,
    int carModelYear,
    int currentKm,
    double carDailyPrice,
    String description) {}

package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

public record CarMaintenanceDto(
    int maintenanceId, String description, LocalDate returnDate, int carId) {}

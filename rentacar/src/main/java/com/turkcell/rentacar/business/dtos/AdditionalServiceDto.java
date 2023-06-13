package com.turkcell.rentacar.business.dtos;

public record AdditionalServiceDto(
    int additionalServiceId, String additionalServiceName, double additionalServiceDailyPrice) {}

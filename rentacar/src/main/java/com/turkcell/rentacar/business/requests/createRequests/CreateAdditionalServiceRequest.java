package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record CreateAdditionalServiceRequest(
    @NotNull @NotBlank @Size(min = 2, max = 25) String additionalServiceName,
    @NotNull @Positive double additionalServiceDailyPrice) {}

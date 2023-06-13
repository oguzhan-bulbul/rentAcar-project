package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;

public record UpdateBrandRequest(
    @NotNull @Positive int brandId, @NotBlank @NotNull @Size(min = 2, max = 20) String brandName) {}

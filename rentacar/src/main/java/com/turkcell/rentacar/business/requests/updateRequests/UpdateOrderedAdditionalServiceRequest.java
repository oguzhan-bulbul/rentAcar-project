package com.turkcell.rentacar.business.requests.updateRequests;

import com.turkcell.rentacar.entities.concretes.AdditionalService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.util.List;

public record UpdateOrderedAdditionalServiceRequest(
    @NotNull @Positive int orderedAdditionalServiceId,
    List<AdditionalService> additionalServiceId,
    @NotNull @Positive int rentId) {}

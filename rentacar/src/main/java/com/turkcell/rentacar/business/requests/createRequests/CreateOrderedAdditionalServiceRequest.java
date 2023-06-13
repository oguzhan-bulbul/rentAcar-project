package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotNull;
import java.util.List;

public record CreateOrderedAdditionalServiceRequest(
    @NotNull int rentId, List<Integer> additionalServices) {}

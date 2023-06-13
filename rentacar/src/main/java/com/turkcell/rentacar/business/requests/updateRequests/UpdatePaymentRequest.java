package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record UpdatePaymentRequest(
    @NotNull @Positive int paymentId,
    @Positive int rentId,
    @Positive int invoiceNo,
    @Positive int orderedAdditionalServiceId,
    @Positive int customerId) {}

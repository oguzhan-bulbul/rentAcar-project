package com.turkcell.rentacar.business.requests.createRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record CreateInvoiceRequest(
    @NotNull @Positive int customerId,
    @NotNull LocalDate startDate,
    @NotNull LocalDate finishDate,
    @NotNull @Positive int rentId) {}

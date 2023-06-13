package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public record UpdateInvoiceRequest(
    @NotNull @Positive int invoiceNo,
    @NotNull @Positive int customerId,
    @NotNull LocalDate creationDate,
    @NotNull LocalDate startDate,
    @NotNull LocalDate finishDate,
    @NotNull @Positive int rentId) {}

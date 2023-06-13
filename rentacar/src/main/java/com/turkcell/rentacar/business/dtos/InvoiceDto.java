package com.turkcell.rentacar.business.dtos;

import java.time.LocalDate;

public record InvoiceDto(
    int invoiceNo,
    int customerId,
    int rentId,
    LocalDate creationDate,
    LocalDate startDate,
    LocalDate finishDate,
    int totalRentDay,
    double totalBill) {}

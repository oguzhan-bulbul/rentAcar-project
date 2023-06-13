package com.turkcell.rentacar.business.dtos;

public record PaymentDto(
    int paymentId, double totalAmount, int rentId, int invoiceId, int customerId) {}

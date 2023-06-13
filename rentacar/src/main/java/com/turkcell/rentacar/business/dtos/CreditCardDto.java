package com.turkcell.rentacar.business.dtos;

public record CreditCardDto(
    String cardHolder, String cardNo, int expirationMonth, int expirationYear, int CVV) {}

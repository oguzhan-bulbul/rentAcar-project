package com.turkcell.rentacar.business.requests.updateRequests;

import com.turkcell.rentacar.api.models.SavedCreditCard;

public record UpdateCreditCardRequest(
    int creditCardId,
    String cardHolder,
    int CVV,
    double totalBalance,
    int expirationMonth,
    int exporationYear,
    SavedCreditCard savedCreditCard) {}

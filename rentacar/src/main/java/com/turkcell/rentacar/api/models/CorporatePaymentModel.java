package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;

public record CorporatePaymentModel(
    CreateRentForCorporateRequest createRentForCorporateRequest,
    CreateCardRequest createCardRequest) {}

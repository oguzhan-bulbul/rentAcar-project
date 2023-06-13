package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;

public record IndividualPaymentModel(
    CreateRentForIndividualRequest createRentForIndividualRequest,
    CreateCardRequest createCardRequest) {}

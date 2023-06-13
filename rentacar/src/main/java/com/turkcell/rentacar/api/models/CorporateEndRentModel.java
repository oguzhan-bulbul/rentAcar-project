package com.turkcell.rentacar.api.models;

import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;

public record CorporateEndRentModel(
    EndRentRequest endRentRequest, CreateCardRequest createCardRequest) {}

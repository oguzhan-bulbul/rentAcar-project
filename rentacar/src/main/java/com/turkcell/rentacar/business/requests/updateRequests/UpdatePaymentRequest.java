package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdatePaymentRequest {

  @NotNull @Positive private int paymentId;

  @Positive private int rentId;

  @Positive private int invoiceNo;

  @Positive private int orderedAdditionalServiceId;

  @Positive private int customerId;
}

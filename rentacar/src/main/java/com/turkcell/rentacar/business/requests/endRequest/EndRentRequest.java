package com.turkcell.rentacar.business.requests.endRequest;

import jakarta.validation.constraints.FutureOrPresent;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class EndRentRequest {

  @NotNull @Positive private int rentId;

  @NotNull @Positive private int returnedKm;

  @NotNull @FutureOrPresent private LocalDate returnDate;
}

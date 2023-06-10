package com.turkcell.rentacar.api.models;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CreateCardRequest {

  @NotNull
  @NotBlank
  @Size(min = 3, max = 35)
  private String cardHolder;

  @NotNull @NotBlank private String cardNo;

  @NotNull
  @Size(min = 3, max = 3)
  private int CVV;

  @NotNull
  @Size(min = 1, max = 2)
  private int expirationMonth;

  @NotNull
  @Size(min = 2, max = 2)
  private int exporationYear;
}

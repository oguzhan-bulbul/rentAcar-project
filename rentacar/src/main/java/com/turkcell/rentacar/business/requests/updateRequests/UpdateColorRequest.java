package com.turkcell.rentacar.business.requests.updateRequests;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateColorRequest {

  @NotNull @Positive private int colorId;

  @NotBlank
  @NotNull
  @Size(min = 2, max = 15)
  private String colorName;
}

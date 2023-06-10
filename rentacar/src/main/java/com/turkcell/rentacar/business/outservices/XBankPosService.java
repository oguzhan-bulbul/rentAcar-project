package com.turkcell.rentacar.business.outservices;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import java.util.Random;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@NoArgsConstructor
public class XBankPosService {

  public Result isCardValid(CreateCardRequest createCardRequest) throws BusinessException {

    Random isSucces = new Random();
    int sayi = isSucces.nextInt(100);

    if (sayi < 2) {
      System.out.println("xbank");
      throw new BusinessException(" x bank Kart gecersiz");
    }
    System.out.println("xbank");
    return new SuccessResult(" x bank Kart gecerli");
  }

  public Result isPaymentSucces() throws BusinessException {

    Random isSucces = new Random();
    int sayi = isSucces.nextInt(100);

    if (sayi < 2) {
      throw new BusinessException("X Bank Odeme gecersiz");
    }

    return new SuccessResult(" X Bank Odeme gecerli");
  }
}

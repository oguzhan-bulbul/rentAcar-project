package com.turkcell.rentacar.business.adapters.posadapters;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.outservices.XBankPosService;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

@Service
@Primary
public class XBankPosAdapter implements PosService {

  public Result pos(CreateCardRequest createCardRequest) throws BusinessException {
    XBankPosService xBankPosService = new XBankPosService();
    xBankPosService.isCardValid(createCardRequest);

    xBankPosService.isPaymentSucces();
    return new SuccessResult("ISBANK ODEME TAMAMLANDI");
  }
}

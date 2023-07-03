package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {

  private final PaymentService paymentService;

  public PaymentsController(PaymentService paymentService) {

    this.paymentService = paymentService;
  }

  @GetMapping("/getAll")
  DataResult<List<PaymentDto>> getAll() {
    return this.paymentService.getAll();
  }

  @PostMapping("/addforindividual/{savedCreditCard}")
  Result addForIndividualCustomer(
      @RequestBody @Valid IndividualPaymentModel paymentModel,
      @RequestParam @PathVariable(value = "savedCreditCard") SavedCreditCard savedCreditCard)
      throws BusinessException {

    return this.paymentService.makePaymentForIndividualCustomer(paymentModel, savedCreditCard);
  }

  @PostMapping("/addforcorporate/{savedCreditCard}")
  Result addForCorporateCustomer(
      @RequestBody @Valid CorporatePaymentModel paymentModel,
      @RequestParam @PathVariable(value = "savedCreditCard") SavedCreditCard savedCreditCard)
      throws BusinessException {

    return this.paymentService.makePaymentForCorporateCustomer(paymentModel, savedCreditCard);
  }

  @GetMapping("/getById")
  DataResult<PaymentDto> getById(int id) throws BusinessException {
    return this.paymentService.getById(id);
  }

  @DeleteMapping("/delete")
  Result deleteById(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
    return this.paymentService.delete(deletePaymentRequest);
  }
}

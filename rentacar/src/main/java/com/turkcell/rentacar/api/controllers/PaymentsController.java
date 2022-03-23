package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.dtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateOrderedAdditionalServiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Rent;

@RestController
@RequestMapping("/api/payments")
public class PaymentsController {
	
	
	private final PaymentService paymentService;
	
	private final PosService posService;
	private final RentService rentService;
	private final InvoiceService invoiceService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	
	
	
	@Autowired
	public PaymentsController(PaymentService paymentService,PosService posService,
			RentService rentService, InvoiceService invoiceService,
			OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
        this.paymentService = paymentService;
		this.posService = posService;
		this.rentService = rentService;
		this.invoiceService = invoiceService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
	
    }


    @GetMapping("/getAll")
    DataResult<List<PaymentListDto>> getAll() {
        return this.paymentService.getAll();
    }
    
    @Transactional(propagation = Propagation.REQUIRED)
    @PostMapping("/addforindividual/{savedCreditCard}")
    Result addForIndividualCustomer(@RequestBody @Valid IndividualPaymentModel paymentModel,
    		@RequestParam @PathVariable(value = "savedCreditCard") SavedCreditCard savedCreditCard) throws BusinessException {
    		
    	return this.paymentService.makePaymentForIndividualCustomer(paymentModel,savedCreditCard);
    }
    
    @Transactional
    @PostMapping("/addforcorporate/{savedCreditCard}")
    Result addForCorporateCustomer(@RequestBody @Valid CorporatePaymentModel paymentModel ,
    		@RequestParam @PathVariable(value = "savedCreditCard") SavedCreditCard savedCreditCard) throws BusinessException {
    		
    	return this.paymentService.makePaymentForCorporateCustomer(paymentModel, savedCreditCard);
    }

    @GetMapping("/getById")
    DataResult<PaymentDto> getById(int id) throws BusinessException {
        return this.paymentService.getById(id);
    }

    @PutMapping("/update")
    Result update(@RequestBody @Valid UpdatePaymentRequest updatePaymentRequest) throws BusinessException {
        return this.paymentService.update(updatePaymentRequest);
    }

    @DeleteMapping("/delete")
    Result deleteById(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
        return this.paymentService.delete(deletePaymentRequest);
    }
    

}

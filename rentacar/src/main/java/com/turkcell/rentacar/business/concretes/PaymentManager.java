package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.dtos.PaymentListDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class PaymentManager implements PaymentService{
	
	private final PaymentDao paymentDao;
	private final ModelMapperService modelMapperService;
	private final RentService rentService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final InvoiceService invoiceService;
	private final PosService posService;
	private final CreditCardService creditCardService;
	
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, 
			InvoiceService invoiceService, 
			RentService rentService, 
			OrderedAdditionalServiceService orderedAdditionalServiceService, PosService posService, CreditCardService creditCardService) {
		
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentService = rentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.invoiceService = invoiceService;
		this.posService = posService;
		this.creditCardService = creditCardService;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		
		List<Payment> result = this.paymentDao.findAll();
		List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService.forDto()
				.map(payment, PaymentListDto.class))
				.collect(Collectors.toList());
		
		for(int i=0; i<result.size();i++) {
			response.get(i).setInvoiceId(result.get(i).getInvoice().getInvoiceNo());
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
		
		return new SuccessDataResult<List<PaymentListDto>>(response,"Rents listed");
	}
	
	@Transactional
	@Override
	public Result makePaymentForIndividualCustomer(IndividualPaymentModel paymentModel, SavedCreditCard savedCreditCard) throws BusinessException {
		
		Rent rent = this.rentService.addForIndividualCustomer(paymentModel.getCreateRentForIndividualRequest()).getData();
    	
    	this.orderedAdditionalServiceService.addWithFields(rent.getRentId(), paymentModel.getCreateRentForIndividualRequest().getAdditionalServices());
    	
    	this.invoiceService.addInvoice(rent.getRentId());
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForIndividualRequest().getIndividualCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    	
    	
    	
		Payment payment = manuelMappingPayment(rent);
		this.paymentDao.save(payment);
		
		return new SuccessResult("saved");
		
	}
	
	@Transactional
	@Override
	public Result makePaymentForCorporateCustomer(CorporatePaymentModel paymentModel, SavedCreditCard savedCreditCard) throws BusinessException {
		
		Rent rent = this.rentService.addForCorporateCustomer(paymentModel.getCreateRentForCorporateRequest()).getData();
    	
    	this.orderedAdditionalServiceService
    	.addWithFields(rent.getRentId(), paymentModel.getCreateRentForCorporateRequest().getAdditionalServices());
    	
    	this.invoiceService.addInvoice(rent.getRentId());
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForCorporateRequest().getCorporateCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    		
		Payment payment = manuelMappingPayment(rent);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult("saved");
		
	}

	private Payment manuelMappingPayment(Rent rent) {
		
		Payment payment = new Payment();	
		payment.setCustomer(rent.getCustomer());
		payment.setInvoice(this.invoiceService.getByRentId(rent.getRentId()));
		payment.setRent(rent);
		payment.setTotalAmount(rent.getBill());
		return payment;
	}

	@Override
	public DataResult<PaymentDto> getById(int id) throws BusinessException {
		
		Payment payment = this.paymentDao.getById(id);
		PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);
		paymentDto.setCustomerId(payment.getCustomer().getCustomerId());
		paymentDto.setInvoiceId(payment.getInvoice().getInvoiceNo());
		
		return new SuccessDataResult<PaymentDto>(paymentDto,"Rent listed");
	}

	@Override
	public Result update(UpdatePaymentRequest updatePaymentRequest) throws BusinessException {

		return null;
	}

	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		
		this.paymentDao.deleteById(deletePaymentRequest.getPaymentId());
        return new SuccessResult("Payment is deleted.");
	}
	
	private void saveCreditCard(CreateCardRequest createCardRequest , SavedCreditCard savedCreditCard, int customerId) throws BusinessException {
		
		if(savedCreditCard == SavedCreditCard.YES) {
    		this.creditCardService.add(createCardRequest,customerId);
    	}
		
	}

}

package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.api.models.CorporatePaymentModel;
import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.api.models.SavedCreditCard;
import com.turkcell.rentacar.business.abstracts.CreditCardService;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rent;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class PaymentManager implements PaymentService{
	
	private final PaymentDao paymentDao;
	private final ModelMapperService modelMapperService;
	private final RentService rentService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final InvoiceService invoiceService;
	private final PosService posService;
	private final CreditCardService creditCardService;
	private final CustomerService customerService;
	
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService, 
			InvoiceService invoiceService, 
			RentService rentService, 
			OrderedAdditionalServiceService orderedAdditionalServiceService, PosService posService, CreditCardService creditCardService, CustomerService customerService) {
		
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentService = rentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.invoiceService = invoiceService;
		this.posService = posService;
		this.creditCardService = creditCardService;
		this.customerService = customerService;
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
		
		return new SuccessDataResult<List<PaymentListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Result makePaymentForIndividualCustomer(IndividualPaymentModel paymentModel, SavedCreditCard savedCreditCard) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(paymentModel.getCreateRentForIndividualRequest().getIndividualCustomerId());
		
		Rent rent = this.rentService.addForIndividualCustomer(paymentModel.getCreateRentForIndividualRequest()).getData();
    	
    	this.orderedAdditionalServiceService.addWithFields(rent.getRentId(), paymentModel.getCreateRentForIndividualRequest().getAdditionalServices());
    	
    	Invoice invoice = this.invoiceService.addInvoice(rent.getRentId()).getData();
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForIndividualRequest().getIndividualCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    	
		Payment payment = manuelMappingPayment(rent,invoice);
		this.paymentDao.save(payment);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
		
	}
	
	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Result makeAdditionalPaymentForIndividualCustomer(int rentId,IndividualPaymentModel paymentModel,
			SavedCreditCard savedCreditCard) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(paymentModel.getCreateRentForIndividualRequest().getIndividualCustomerId());
		
		Rent rent = this.rentService.createRentForIndividualCustomer(paymentModel.getCreateRentForIndividualRequest()).getData();
		rent.setRentId(rentId);
		rent.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getEntityByRentId(rent.getRentId()));
		
    	Invoice invoice = this.invoiceService.addInvoice(rent).getData();
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForIndividualRequest().getIndividualCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    	
    	Rent baseRent = this.rentService.getRentEntityById(rentId);
    	baseRent.setFinishDate(rent.getFinishDate());
    	baseRent.setBill(baseRent.getBill()+rent.getBill());
    	this.rentService.saveRentEntity(baseRent);
    		
		Payment payment = manuelMappingPayment(rent,invoice);				
		this.paymentDao.save(payment);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Result makePaymentForCorporateCustomer(CorporatePaymentModel paymentModel
			, SavedCreditCard savedCreditCard) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(paymentModel.getCreateRentForCorporateRequest().getCorporateCustomerId());
		
		Rent rent = this.rentService.addForCorporateCustomer(paymentModel.getCreateRentForCorporateRequest()).getData();
    	
    	this.orderedAdditionalServiceService
    	.addWithFields(rent.getRentId(), paymentModel.getCreateRentForCorporateRequest().getAdditionalServices());
    	
    	Invoice invoice = this.invoiceService.addInvoice(rent.getRentId()).getData();
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForCorporateRequest().getCorporateCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    		
		Payment payment = manuelMappingPayment(rent, invoice);
		
		this.paymentDao.save(payment);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
		
	}
	
	
	@Transactional(rollbackFor = BusinessException.class)
	@Override
	public Result makeAdditionalPaymentForCorporateCustomer(int rentId,CorporatePaymentModel paymentModel,
			SavedCreditCard savedCreditCard) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(paymentModel.getCreateRentForCorporateRequest().getCorporateCustomerId());
		
		Rent rent = this.rentService.createRentForCorporateCustomer(paymentModel.getCreateRentForCorporateRequest()).getData();
		rent.setRentId(rentId);
		rent.setOrderedAdditionalServices(this.orderedAdditionalServiceService.getEntityByRentId(rent.getRentId()));
		
    	Invoice invoice = this.invoiceService.addInvoice(rent).getData();
    	
    	saveCreditCard(paymentModel.getCreateCardRequest(), savedCreditCard, 
    			paymentModel.getCreateRentForCorporateRequest().getCorporateCustomerId());
    	
    	this.posService.pos(paymentModel.getCreateCardRequest());
    	
    	Rent baseRent = this.rentService.getRentEntityById(rentId);
    	baseRent.setFinishDate(rent.getFinishDate());
    	baseRent.setBill(baseRent.getBill()+rent.getBill());
    	this.rentService.saveRentEntity(baseRent);
    		
		Payment payment = manuelMappingPayment(rent,invoice);				
		this.paymentDao.save(payment);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<PaymentDto> getById(int id) throws BusinessException {
		
		Payment payment = this.paymentDao.getById(id);
		PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);
		paymentDto.setCustomerId(payment.getCustomer().getCustomerId());
		paymentDto.setInvoiceId(payment.getInvoice().getInvoiceNo());
		
		return new SuccessDataResult<PaymentDto>(paymentDto,ResultMessages.LISTEDSUCCESSFUL);
	}


	@Override
	public Result delete(DeletePaymentRequest deletePaymentRequest) throws BusinessException {
		
		this.paymentDao.deleteById(deletePaymentRequest.getPaymentId());
        return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	private void saveCreditCard(CreateCardRequest createCardRequest , SavedCreditCard savedCreditCard, int customerId) throws BusinessException {
		
		if(savedCreditCard == SavedCreditCard.YES) {
    		this.creditCardService.add(createCardRequest,customerId);
    	}
		
	}
	
	private Payment manuelMappingPayment(Rent rent , Invoice invoice) {
		
		Payment payment = new Payment();	
		payment.setCustomer(rent.getCustomer());
		payment.setInvoice(invoice);
		payment.setRent(invoice.getRent());
		payment.setTotalAmount(rent.getBill());
		return payment;
	}

	



}

package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.OrderedAdditionalServiceService;
import com.turkcell.rentacar.business.abstracts.PaymentService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.PaymentDto;
import com.turkcell.rentacar.business.dtos.PaymentListDto;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreatePaymentRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeletePaymentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdatePaymentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.PaymentDao;
import com.turkcell.rentacar.entities.concretes.OrderedAdditionalService;
import com.turkcell.rentacar.entities.concretes.Payment;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class PaymentManager implements PaymentService{
	
	private final PaymentDao paymentDao;
	private final ModelMapperService modelMapperService;
	private final RentService rentService;
	private final OrderedAdditionalServiceService orderedAdditionalServiceService;
	private final CustomerService customerService;
	private final InvoiceService invoiceService;
	
	
	public PaymentManager(PaymentDao paymentDao, ModelMapperService modelMapperService,
			CustomerService customerService, 
			InvoiceService invoiceService, 
			RentService rentService, 
			OrderedAdditionalServiceService orderedAdditionalServiceService) {
		
		this.paymentDao = paymentDao;
		this.modelMapperService = modelMapperService;
		this.rentService = rentService;
		this.orderedAdditionalServiceService = orderedAdditionalServiceService;
		this.customerService = customerService;
		this.invoiceService = invoiceService;
	}

	@Override
	public DataResult<List<PaymentListDto>> getAll() {
		
		List<Payment> result = this.paymentDao.findAll();
		List<PaymentListDto> response = result.stream().map(payment -> this.modelMapperService.forDto()
				.map(payment, PaymentListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<PaymentListDto>>(response,"Rents listed");
	}

	@Override
	public Result add(int rentId) throws BusinessException {
		
		Rent rent = this.rentService.getRentEntityById(rentId);
		
		Payment payment = new Payment();	
		payment.setCustomer(rent.getCustomer());
		payment.setInvoice(this.invoiceService.getByRentId(rentId));
		payment.setOrderedAdditionalService(rent.getOrderedAdditionalServices());
		payment.setRent(rent);
		payment.setTotalAmount(rent.getBill());
		this.paymentDao.save(payment);
		return new SuccessResult("saved");
		
	}

	@Override
	public DataResult<PaymentDto> getById(int id) throws BusinessException {
		Payment payment = this.paymentDao.getById(id);
		PaymentDto paymentDto = this.modelMapperService.forDto().map(payment, PaymentDto.class);
		
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

}

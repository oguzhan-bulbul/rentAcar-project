package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;
import com.turkcell.rentacar.business.abstracts.CustomerService;
import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.InvoiceDto;
import com.turkcell.rentacar.business.dtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.InvoiceDao;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rent;

@Service
public class InvoiceManager implements InvoiceService{
	
	private InvoiceDao invoiceDao;
	private ModelMapperService modelMapperService;
	private final RentService rentService;
	private final CustomerService customerService;
	
	
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentService rentService, CustomerService customerService) {

		this.rentService = rentService;
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		this.customerService = customerService;
		
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAll() {
		
		List<Invoice> result = this.invoiceDao.findAll();
		List<InvoiceListDto> response = result.stream()
				.map(invoice -> this.modelMapperService.forDto()
						.map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
		
		for(int i=0; i<result.size();i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
	
		return new SuccessDataResult<List<InvoiceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(createInvoiceRequest.getCustomerId());
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(createInvoiceRequest.getRentId());
		
		Invoice result = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		this.invoiceDao.save(result);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<InvoiceDto> getById(int id) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(id);
		
		Invoice result = this.invoiceDao.getById(id);
		InvoiceDto response = this.modelMapperService.forDto().map(result, InvoiceDto.class);
		
		return new SuccessDataResult<InvoiceDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(updateInvoiceRequest.getInvoiceNo());
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(updateInvoiceRequest.getCustomerId());
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(updateInvoiceRequest.getRentId());
		
		Invoice result = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		this.invoiceDao.save(result);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(deleteInvoiceRequest.getInvoiceNo());
		
		this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceNo());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByCustomerId(String id) throws BusinessException {
		
		this.customerService.checkIfCustomerDoesNotExistsByIdIsSuccess(id);
		
		List<Invoice> result = this.invoiceDao.getAllByCustomer_CustomerId(id);
		List<InvoiceListDto> response =result.stream()
				.map(invoice -> this.modelMapperService.forDto()
						.map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
		
		for(int i=0; i<result.size();i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public DataResult<List<InvoiceListDto>> getAllBetweenDates(LocalDate startDate, LocalDate finishDate) {
		
		List<Invoice> result = this.invoiceDao.getAllByCreationDateBetween(startDate, finishDate);
		List<InvoiceListDto> response =result.stream()
				.map(invoice -> this.modelMapperService.forDto()
						.map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
		
		for(int i=0; i<result.size();i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<Invoice> addInvoice(int rentId) throws BusinessException {
		
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(rentId);
		
		Invoice invoice = new Invoice();
		setInvoiceFields(invoice, rentId);
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<Invoice>(invoice,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	@Override
	public DataResult<Invoice> addInvoice(Rent rent) throws BusinessException {
		
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(rent.getRentId());
		
		Invoice invoice = new Invoice();
		setInvoiceFields(invoice, rent);
		this.invoiceDao.save(invoice);
		
		return new SuccessDataResult<Invoice>(invoice,ResultMessages.ADDEDSUCCESSFUL);
	}
	
	public Invoice getByRentId(int id) throws BusinessException {
		
		this.rentService.checkIfRentDoesNotExistsByIdIsSuccess(id);
		
		return this.invoiceDao.getByRent_RentId(id);
	}
	
	private void checkIfInvoiceDoesNotExistById(int id) throws BusinessException{
		
		if(!this.invoiceDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.INVOICENOTFOUND);
			
		}				
	}
	
	private void setInvoiceFields(Invoice invoice , int rentId) {
		
		Rent rent = this.rentService.getRentEntityById(rentId);
		invoice.setRent(rent);
		invoice.setTotalRentDay((int)ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate()));
        invoice.setCustomer(rent.getCustomer());
        invoice.setTotalBill(rent.getBill());
        invoice.setStartDate(rent.getStartDate());
        invoice.setFinishDate(rent.getFinishDate());
        invoice.setCreationDate(LocalDate.now());
        invoice.setInvoiceNo(0);
	}
	
	private void setInvoiceFields(Invoice invoice , Rent rent) {
		
		invoice.setRent(rent);
		invoice.setTotalRentDay((int)ChronoUnit.DAYS.between(rent.getStartDate(), rent.getFinishDate()));
        invoice.setCustomer(rent.getCustomer());
        invoice.setTotalBill(rent.getBill());
        invoice.setStartDate(rent.getStartDate());
        invoice.setFinishDate(rent.getFinishDate());
        invoice.setCreationDate(LocalDate.now());
        invoice.setInvoiceNo(0);
	}

	@Override
	public Result saveInvoiceEntity(Invoice invoice) {
		this.invoiceDao.save(invoice);
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public Invoice getByIdEntity(int id) {
		
		return this.invoiceDao.getById(id);
	}

	

}

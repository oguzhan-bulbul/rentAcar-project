package com.turkcell.rentacar.business.concretes;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
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
	
	
	public InvoiceManager(InvoiceDao invoiceDao, ModelMapperService modelMapperService, RentService rentService) {

		this.rentService = rentService;
		this.invoiceDao = invoiceDao;
		this.modelMapperService = modelMapperService;
		
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
	
		return new SuccessDataResult<List<InvoiceListDto>>(response,"Invoices Listed");
	}

	@Override
	public Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException {
		
		Invoice result = this.modelMapperService.forRequest().map(createInvoiceRequest, Invoice.class);
		this.invoiceDao.save(result);
		
		return new SuccessResult("Invoice saved");
	}

	@Override
	public DataResult<InvoiceDto> getById(int id) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(id);
		
		Invoice result = this.invoiceDao.getById(id);
		InvoiceDto response = this.modelMapperService.forDto().map(result, InvoiceDto.class);
		
		return new SuccessDataResult<InvoiceDto>(response,"Corporate Customer listed");
	}

	@Override
	public Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(updateInvoiceRequest.getInvoiceNo());
		
		Invoice result = this.modelMapperService.forRequest().map(updateInvoiceRequest, Invoice.class);
		this.invoiceDao.save(result);
		
		return new SuccessResult("Invoice updated");
	}

	@Override
	public Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException {
		
		checkIfInvoiceDoesNotExistById(deleteInvoiceRequest.getInvoiceNo());
		
		this.invoiceDao.deleteById(deleteInvoiceRequest.getInvoiceNo());
		
		return new SuccessResult("Invoice deleted.");
	}
	
	@Override
	public DataResult<List<InvoiceListDto>> getAllByCustomerId(int id) {
		
		List<Invoice> result = this.invoiceDao.getAllByCustomer_CustomerId(id);
		List<InvoiceListDto> response =result.stream()
				.map(invoice -> this.modelMapperService.forDto()
						.map(invoice, InvoiceListDto.class)).collect(Collectors.toList());
		
		for(int i=0; i<result.size();i++) {
			response.get(i).setCustomerId(result.get(i).getCustomer().getCustomerId());
		}
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,"Customer's invoices listed");
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
		
		return new SuccessDataResult<List<InvoiceListDto>>(response,"Voices listed.");
	}

	@Override
	public Result addInvoice(int rentId) throws BusinessException {
		
		Invoice invoice = new Invoice();
		setInvoiceFields(invoice, rentId);
		this.invoiceDao.save(invoice);
		
		return new SuccessResult("Invoice saved");
	}
	
	public Invoice getByRentId(int id) {
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

	

}

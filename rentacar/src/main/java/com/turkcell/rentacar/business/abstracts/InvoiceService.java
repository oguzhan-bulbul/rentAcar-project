package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.InvoiceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;
import com.turkcell.rentacar.entities.concretes.Rent;
import java.time.LocalDate;
import java.util.List;

public interface InvoiceService {
	
	DataResult<List<InvoiceListDto>> getAll();
	
	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;
	
	DataResult<InvoiceDto> getById(int id) throws BusinessException;
	
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;
	
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;
	
	DataResult<Invoice> addInvoice(int rentId) throws BusinessException;
	
	DataResult<List<InvoiceListDto>> getAllByCustomerId(int id) throws BusinessException;
	
	DataResult<List<InvoiceListDto>> getAllBetweenDates(LocalDate startDate, LocalDate finishDate);
	
	Invoice getByRentId(int id) throws BusinessException;
	
	Invoice getByIdEntity(int id);
	
	Result saveInvoiceEntity(Invoice invoice);
	
	DataResult<Invoice> addInvoice(Rent rent) throws BusinessException;

}

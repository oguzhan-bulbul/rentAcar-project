package com.turkcell.rentacar.business.abstracts;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import com.turkcell.rentacar.business.dtos.InvoiceDto;
import com.turkcell.rentacar.business.dtos.InvoiceListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;

import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.entities.concretes.Invoice;

public interface InvoiceService {
	
	DataResult<List<InvoiceListDto>> getAll();
	
	Result add(CreateInvoiceRequest createInvoiceRequest) throws BusinessException;
	
	DataResult<InvoiceDto> getById(int id) throws BusinessException;
	
	Result update(UpdateInvoiceRequest updateInvoiceRequest) throws BusinessException;
	
	Result delete(DeleteInvoiceRequest deleteInvoiceRequest) throws BusinessException;
	
	Result addInvoice(Invoice invoice) throws BusinessException;
	
	DataResult<List<InvoiceListDto>> getAllByCustomerId(int id);
	
	DataResult<List<InvoiceListDto>> getAllBetweenDates(LocalDate startDate, LocalDate finishDate);
	
	Invoice getByRentId(int id);

}

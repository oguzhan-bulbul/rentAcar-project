package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.InvoiceService;
import com.turkcell.rentacar.business.dtos.InvoiceDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateInvoiceRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteInvoiceRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateInvoiceRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import jakarta.validation.Valid;
import java.time.LocalDate;
import java.util.List;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/invoices")
public class InvoicesController {

  private final InvoiceService invoiceService;

  public InvoicesController(InvoiceService invoiceService) {
    this.invoiceService = invoiceService;
  }

  @GetMapping("/getall")
  public DataResult<List<InvoiceDto>> getAll() {
    return this.invoiceService.getAll();
  }

  @PostMapping("/add")
  public Result add(@RequestBody @Valid CreateInvoiceRequest createInvoiceRequest)
      throws BusinessException {
    return this.invoiceService.add(createInvoiceRequest);
  }

  @GetMapping("/getbyid")
  public DataResult<InvoiceDto> getById(@RequestParam int id) throws BusinessException {
    return this.invoiceService.getById(id);
  }

  @PutMapping("/update")
  public Result update(@RequestBody @Valid UpdateInvoiceRequest updateInvoiceRequest)
      throws BusinessException {
    return this.invoiceService.update(updateInvoiceRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestBody @Valid DeleteInvoiceRequest deleteInvoiceRequest)
      throws BusinessException {
    return this.invoiceService.delete(deleteInvoiceRequest);
  }

  @GetMapping("/getallbycustomerid")
  public DataResult<List<InvoiceDto>> getAllByCustomerId(@RequestParam int id)
      throws BusinessException {
    return this.invoiceService.getAllByCustomerId(id);
  }

  @GetMapping("/getAllBetweenDates/{first_date}/{second_date}")
  public DataResult<List<InvoiceDto>> getAllInvoicesBetweenDates(
      @PathVariable(value = "first_date") @DateTimeFormat(pattern = "yyyy-MM-dd")
          LocalDate startDate,
      @PathVariable(value = "second_date") @DateTimeFormat(pattern = "yyyy-MM-dd")
          LocalDate finishDate)
      throws BusinessException {
    return this.invoiceService.getAllBetweenDates(startDate, finishDate);
  }
}

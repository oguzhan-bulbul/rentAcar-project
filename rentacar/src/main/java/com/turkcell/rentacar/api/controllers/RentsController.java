package com.turkcell.rentacar.api.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.turkcell.rentacar.api.models.CorporateEndRentModel;
import com.turkcell.rentacar.api.models.IndividualEndRentModel;
import com.turkcell.rentacar.api.models.IndividualPaymentModel;
import com.turkcell.rentacar.business.abstracts.RentService;
import com.turkcell.rentacar.business.dtos.RentDto;
import com.turkcell.rentacar.business.dtos.RentListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForCorporateRequest;
import com.turkcell.rentacar.business.requests.createRequests.CreateRentForIndividualRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteRentRequest;
import com.turkcell.rentacar.business.requests.endRequest.EndRentRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateRentRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
@RestController
@RequestMapping("/api/rents")
public class RentsController {
	private RentService rentService;

	@Autowired
    public RentsController(RentService rentService) {
        this.rentService = rentService;
    }

    @GetMapping("/getall")
    public DataResult<List<RentListDto>> getAll(){
    
       return this.rentService.getAll();
    }

    /*@PostMapping("/createforindividual")
    public Result add(@RequestBody @Valid CreateRentForIndividualRequest createRentRequest) throws BusinessException{
    	
        return this.rentService.addForIndividualCustomer(createRentRequest);
    }*/
    
    /*
    @PostMapping("/createforcorporate")
    public Result add(@RequestBody @Valid CreateRentForCorporateRequest createRentRequest) throws BusinessException{
    	
        return this.rentService.addForCorporateCustomer(createRentRequest);
    }*/
    
    @PostMapping("/endrentforindividual")
    public Result endRentForIndividual(@RequestBody @Valid IndividualEndRentModel paymentModel) throws BusinessException {
    	
    	return this.rentService.endRentForIndividual(paymentModel);
    }
    
    @PostMapping("/endrentforcorporate")
    public Result endRentForCorporate(@RequestBody @Valid CorporateEndRentModel paymentModel) throws BusinessException {
    	
    	return this.rentService.endRentForCorporate(paymentModel);
    }

    @GetMapping("/getById")
    public DataResult<RentDto> getById(@RequestParam int id) throws BusinessException{
        return this.rentService.getById(id);
    }

    @PutMapping("/update")
    public Result update(@RequestBody @Valid UpdateRentRequest updateRentRequest) throws BusinessException{
        return this.rentService.update(updateRentRequest);
    }

    @DeleteMapping("/delete")
    public Result deleteByRentId(@RequestBody @Valid DeleteRentRequest deleteRentRequest) throws BusinessException{
        return this.rentService.delete(deleteRentRequest);
    }
}

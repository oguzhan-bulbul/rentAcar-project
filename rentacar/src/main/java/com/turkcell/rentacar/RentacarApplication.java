package com.turkcell.rentacar;

import java.util.HashMap;
import java.util.Map;

import org.modelmapper.ModelMapper;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.http.HttpStatus;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.ErrorDataResult;


@SpringBootApplication
@RestControllerAdvice
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan("com.turkcell.rentacar.entities")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RentacarApplication {

	public static void main(String[] args) {
		SpringApplication.run(RentacarApplication.class, args);
	}
	
	@Bean     
	public ModelMapper getModelMapper(){
		return new ModelMapper();     
	}
	
	@ExceptionHandler
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleValidationExceptions(MethodArgumentNotValidException methodArgumentNotValidException){
        Map<String,String> validationErrors = new HashMap<String,String>();
        for (FieldError fieldError: methodArgumentNotValidException.getBindingResult().getFieldErrors()) {
            validationErrors.put(fieldError.getField(),fieldError.getDefaultMessage());
        }
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(validationErrors,"Validation Error");
        return errorDataResult;
    }
    @ExceptionHandler
    @ResponseStatus(code= HttpStatus.BAD_REQUEST)
    public ErrorDataResult<Object> handleBusinessExceptions(BusinessException businessException){
        ErrorDataResult<Object> errorDataResult = new ErrorDataResult<Object>(businessException.getMessage(),"Business Exception Error");
        return errorDataResult;
    }
			
	

}

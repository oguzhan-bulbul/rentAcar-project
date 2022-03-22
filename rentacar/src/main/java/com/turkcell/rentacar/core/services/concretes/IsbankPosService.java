package com.turkcell.rentacar.core.services.concretes;

import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.core.services.abstracts.PosService;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Primary
public class IsbankPosService implements PosService{
	

	@Override
	public Result isCardValid(CreateCardRequest createCardRequest) throws BusinessException {
		
		Random isSucces = new Random();
		int sayi = isSucces.nextInt(100);
		
		if(sayi < 50) {
			System.out.println("isbank");
			throw new BusinessException("Kart gecersiz");
			
		}
		System.out.println("isbank");
		
		return new SuccessResult("Kart gecerli");
	}

	@Override
	public Result isPaymentSucces(double amount) throws BusinessException {
		
		Random isSucces = new Random();
		int sayi = isSucces.nextInt(100);
		
		if(sayi < 50) {
			throw new BusinessException("Is bank Odeme gecersiz");
		}
		
		return new SuccessResult("Is Bank Odeme gecerli");
	}
	
	

}

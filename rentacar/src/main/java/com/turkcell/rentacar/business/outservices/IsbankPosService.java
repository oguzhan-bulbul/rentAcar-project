package com.turkcell.rentacar.business.outservices;

import java.util.Random;

import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.business.abstracts.PosService;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

import lombok.NoArgsConstructor;

@Service
@NoArgsConstructor
@Primary
@Transactional
public class IsbankPosService{
	


	public Result isCardValid(CreateCardRequest createCardRequest) throws BusinessException {
		
		Random rand = new Random();
		int sayi = rand.nextInt(100);
		
		if(sayi < 2) {
			System.out.println("isbank");
			throw new BusinessException("Kart gecersiz");
			
		}
		System.out.println("isbank");
		
		return new SuccessResult("Kart gecerli");
	}


	public Result isPaymentSucces(double amount) throws BusinessException {
		
		Random rand = new Random();
		int sayi = rand.nextInt(100);
		
		if(sayi < 98) {
			throw new BusinessException("Is bank Odeme gecersiz");
		}
		
		return new SuccessResult("Is Bank Odeme gecerli");
	}
	
	

}

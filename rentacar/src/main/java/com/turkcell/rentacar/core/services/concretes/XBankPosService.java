package com.turkcell.rentacar.core.services.concretes;

import java.util.Random;

import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.api.models.CreateCardRequest;
import com.turkcell.rentacar.core.services.abstracts.PosService;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;

import lombok.NoArgsConstructor;

@Service("posService")
@NoArgsConstructor
@Profile("xbank")
public class XBankPosService implements PosService{
	
	@Override
	public Result isCardValid(CreateCardRequest createCardRequest) throws BusinessException {
		
		Random isSucces = new Random();
		int sayi = isSucces.nextInt(100);
		
		if(sayi < 50) {
			System.out.println("xbank");
			throw new BusinessException(" x bank Kart gecersiz");
		}
		System.out.println("xbank");
		return new SuccessResult(" x bank Kart gecerli");
	}

	@Override
	public Result isPaymentSucces(double amount) throws BusinessException {
		
		Random isSucces = new Random();
		int sayi = isSucces.nextInt(100);
		
		if(sayi < 50) {
			throw new BusinessException("X Bank Odeme gecersiz");
		}
		
		return new SuccessResult(" X Bank Odeme gecerli");
	}

}

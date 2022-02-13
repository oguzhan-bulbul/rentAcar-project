package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;

public interface ColorService {
	
	List<ColorListDto> getAll();
	void save(CreateColorRequest createColorRequest) throws Exception;
	ColorListDto getById(int id);

}

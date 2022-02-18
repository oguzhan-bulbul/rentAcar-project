package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;

public interface ColorService {
	
	List<ColorListDto> getAll();
	
	void save(CreateColorRequest createColorRequest) throws Exception;
	
	ColorDto getById(int id) throws Exception;
	
	void update(UpdateColorRequest updateColorRequest);
	
	void delete(DeleteColorRequest deleteColorRequest);

}

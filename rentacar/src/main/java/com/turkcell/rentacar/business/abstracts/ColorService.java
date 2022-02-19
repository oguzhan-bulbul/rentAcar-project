package com.turkcell.rentacar.business.abstracts;

import java.util.List;

import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;

public interface ColorService {
	
	DataResult<List<ColorListDto>> getAll();
	
	Result save(CreateColorRequest createColorRequest) throws Exception;
	
	DataResult<ColorDto> getById(int id) throws Exception;
	
	Result update(UpdateColorRequest updateColorRequest);
	
	Result delete(DeleteColorRequest deleteColorRequest);

}

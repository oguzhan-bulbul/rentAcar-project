package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
@Service
public class ColorManager implements ColorService{
	
	private final ColorDao colorDao;
	private final ModelMapperService modelMapperService;
			
	@Autowired
	public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService=modelMapperService;
		
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		
		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto()
				.map(color,ColorListDto.class)).collect(Collectors.toList());
				
		return new SuccessDataResult<List<ColorListDto>>(response, "Colors listed.");
		
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException{
		
		checkIfColorExistsByName(createColorRequest.getColorName());		
		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);	
		this.colorDao.save(color);
		
		return new SuccessResult("Color added.");
		
	}
	
	@Override
	public DataResult<ColorDto> getById(int id) throws BusinessException{
		
		
		checkIfColorDoesNotExistsById(id);
		Color result = this.colorDao.getById(id);
		ColorDto response = this.modelMapperService.forDto().map(result, ColorDto.class);
			
		return new SuccessDataResult<ColorDto>(response,"The color is listed.");
			
				
	}
	
	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
			
		checkIfColorDoesNotExistsById(updateColorRequest.getColorId());
		Color color = this.colorDao.getById(updateColorRequest.getColorId());
		color=this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
			
		return new SuccessResult("The color is updated");
			
			
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		
		checkIfColorDoesNotExistsById(deleteColorRequest.getColorId());
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		this.colorDao.delete(color);
			
		return new SuccessResult("The color is deleted");
			
				
	}
	
	
	
	private void checkIfColorExistsByName(String name) throws BusinessException{
		
		if(this.colorDao.existsByColorName(name)) {
			
			throw new BusinessException("Color is already exists.");
			
		}		
	}
	
	private void checkIfColorDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.colorDao.existsById(id)) {
			
			throw new BusinessException("Color does not exists");
			
		}		
	}
}

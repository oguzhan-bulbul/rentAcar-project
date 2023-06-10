package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
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
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class ColorManager implements ColorService{
	
	private final ColorDao colorDao;
	private final ModelMapperService modelMapperService;
			
	
	public ColorManager(ColorDao colorDao,ModelMapperService modelMapperService) {
		
		this.colorDao = colorDao;
		this.modelMapperService=modelMapperService;
		
	}

	@Override
	public DataResult<List<ColorListDto>> getAll() {
		
		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto()
				.map(color,ColorListDto.class)).collect(Collectors.toList());
				
		return new SuccessDataResult<List<ColorListDto>>(response, ResultMessages.LISTEDSUCCESSFUL);
		
	}

	@Override
	public Result add(CreateColorRequest createColorRequest) throws BusinessException{
		
		String upperCase = createColorRequest.getColorName().toUpperCase();
		createColorRequest.setColorName(upperCase);
		
		checkIfColorExistsByName(createColorRequest.getColorName());
		
		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);	
		this.colorDao.save(color);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
		
	}
	
	@Override
	public DataResult<ColorDto> getById(int id) throws BusinessException{
		
		
		checkIfColorDoesNotExistsById(id);
		
		Color result = this.colorDao.getById(id);
		ColorDto response = this.modelMapperService.forDto().map(result, ColorDto.class);
			
		return new SuccessDataResult<ColorDto>(response,ResultMessages.LISTEDSUCCESSFUL);
			
				
	}
	
	@Override
	public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {
		
		String upperCase = updateColorRequest.getColorName().toUpperCase();
		updateColorRequest.setColorName(upperCase);
			
		checkIfColorDoesNotExistsById(updateColorRequest.getColorId());
		
		Color color = this.colorDao.getById(updateColorRequest.getColorId());
		color=this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
			
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
			
			
	}

	@Override
	public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {
		
		checkIfColorDoesNotExistsById(deleteColorRequest.getColorId());
		
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		this.colorDao.delete(color);
			
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
			
				
	}
	
	
	
	private void checkIfColorExistsByName(String name) throws BusinessException{
		
		if(this.colorDao.existsByColorName(name)) {
			
			throw new BusinessException(BusinessMessages.COLOREXISTS);
			
		}		
	}
	
	private void checkIfColorDoesNotExistsById(int id) throws BusinessException{
		
		if(!this.colorDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.COLORNOTFOUND);
			
		}		
	}
	
	public Result checkIfColorDoesNotExists(int id) throws BusinessException {
		
		checkIfColorDoesNotExistsById(id);
		
		return new SuccessResult(ResultMessages.AVAILABLE);
	}
}

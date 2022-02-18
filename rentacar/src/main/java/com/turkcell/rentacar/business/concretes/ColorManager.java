package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorDto;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.business.requests.DeleteColorRequest;
import com.turkcell.rentacar.business.requests.UpdateColorRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
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
	public List<ColorListDto> getAll() {
		
		List<Color> result = this.colorDao.findAll();
		List<ColorListDto> response = result.stream().map(color->this.modelMapperService.forDto()
				.map(color,ColorListDto.class)).collect(Collectors.toList());
		
		
		return response;
	}

	@Override
	public void save(CreateColorRequest createColorRequest) throws Exception {
		
		checkIfColorExistsByName(createColorRequest.getColorName());
		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);
		System.out.println(createColorRequest.getColorName());	
		this.colorDao.save(color);
		
	}
	
	@Override
	public ColorDto getById(int id) throws Exception {
		checkIfColorDoesNotExistsById(id);
		Color result = this.colorDao.getById(id);
		ColorDto response = this.modelMapperService.forDto().map(result, ColorDto.class);
		return response;
	}
	
	@Override
	public void update(UpdateColorRequest updateColorRequest) {
		
		Color color = this.colorDao.getById(updateColorRequest.getColorId());
		color=this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
		this.colorDao.save(color);
		
	}

	@Override
	public void delete(DeleteColorRequest deleteColorRequest) {
		
		Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
		this.colorDao.delete(color);
		
	}
	
	
	
	private void checkIfColorExistsByName(String name) throws Exception {
		
		if(this.colorDao.existsByName(name)) {
			throw new Exception("This color is already exists");
		}
		
	}
	
	private void checkIfColorDoesNotExistsById(int id) throws Exception {
		
		if(!this.colorDao.existsById(id)) {
			throw new Exception("This color is doesn't exists.");
		}
		
	}




	
	

}

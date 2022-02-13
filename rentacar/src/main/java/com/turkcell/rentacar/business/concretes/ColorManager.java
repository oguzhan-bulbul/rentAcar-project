package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.dtos.ColorListDto;
import com.turkcell.rentacar.business.requests.CreateColorRequest;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
@Service
public class ColorManager implements ColorService{
	
	private final ColorDao colorDao;
	private final ModelMapperService modelMapperService;
			

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
		Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);
		System.out.println(createColorRequest.getName());
		
		checkIfColorExist(color);
		this.colorDao.save(color);
	}
	@Override
	public ColorListDto getById(int id) {
		Color result = this.colorDao.getById(id);
		System.out.println(result.getName());
		ColorListDto response = this.modelMapperService.forDto().map(result, ColorListDto.class);
		return response;
	}
	
	
	
	private void checkIfColorExist(Color color) throws Exception {
		if(this.colorDao.existsByName(color.getName())) {
			throw new Exception("This color is already exists");
		}
	}


	
	

}

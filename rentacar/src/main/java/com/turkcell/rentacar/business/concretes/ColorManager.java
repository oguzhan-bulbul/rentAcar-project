package com.turkcell.rentacar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.dataAccess.abstracts.ColorDao;
import com.turkcell.rentacar.entities.concretes.Color;
@Service
public class ColorManager implements ColorService{
	
	private final ColorDao colorDao;
			

	public ColorManager(ColorDao colorDao) {
		this.colorDao = colorDao;
	}

	@Override
	public List<Color> getAll() {
		return this.colorDao.findAll();
	}

	@Override
	public void save(Color color) throws Exception {
		checkIfColorExist(color);
		this.colorDao.save(color);
	}
	
	void checkIfColorExist(Color color) throws Exception {
		if(this.colorDao.existsByName(color.getName())) {
			throw new Exception("This brand is already exists");
		}
	}

}

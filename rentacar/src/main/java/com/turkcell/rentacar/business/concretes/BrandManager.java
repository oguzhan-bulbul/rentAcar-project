package com.turkcell.rentacar.business.concretes;

import java.util.List;

import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
@Service
public class BrandManager implements BrandService{
	
	private final BrandDao brandDao;
	

	public BrandManager(BrandDao brandDao) {
		this.brandDao = brandDao;
	}
	
	
	@Override
	public List<Brand> getAll() {
		
		return this.brandDao.findAll();
	}

	@Override
	public void save(Brand brand) throws Exception {
		
		checkIfBrandExist(brand);
		this.brandDao.save(brand);
	}
	
	private void checkIfBrandExist(Brand brand) throws Exception {
		if(this.brandDao.existsByName(brand.getName())) {
			throw new Exception("This brand is already exists");
		}
		
	}

}

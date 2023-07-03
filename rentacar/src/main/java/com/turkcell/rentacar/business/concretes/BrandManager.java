package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.BrandDao;
import com.turkcell.rentacar.entities.concretes.Brand;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class BrandManager implements BrandService {

  private final BrandDao brandDao;
  private final ModelMapperService modelMapperService;

  public BrandManager(BrandDao brandDao, ModelMapperService modelMapperService) {

    this.brandDao = brandDao;
    this.modelMapperService = modelMapperService;
  }

  @Override
  public DataResult<List<BrandDto>> getAll() {

    List<Brand> result = this.brandDao.findAll();
    List<BrandDto> response =
        result.stream()
            .map(brand -> this.modelMapperService.forDto().map(brand, BrandDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<List<BrandDto>>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateBrandRequest createBrandRequest) throws BusinessException {

    checkIfBrandExistsByName(createBrandRequest.brandName());

    Brand brand = this.modelMapperService.forRequest().map(createBrandRequest, Brand.class);
    this.brandDao.save(brand);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<BrandDto> getById(int id) throws BusinessException {

    checkIfBrandDoesNotExistsById(id);
    Brand result = this.brandDao.getById(id);
    BrandDto response = this.modelMapperService.forDto().map(result, BrandDto.class);

    return new SuccessDataResult<BrandDto>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateBrandRequest updateBrandRequest) throws BusinessException {

    checkIfBrandDoesNotExistsById(updateBrandRequest.brandId());
    Brand brand = this.modelMapperService.forRequest().map(updateBrandRequest, Brand.class);
    this.brandDao.save(brand);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteBrandRequest deleteBrandRequest) throws BusinessException {

    checkIfBrandDoesNotExistsById(deleteBrandRequest.brandId());

    Brand brand = this.modelMapperService.forRequest().map(deleteBrandRequest, Brand.class);
    this.brandDao.delete(brand);

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  public Result checkIfBrandDoesNotExists(int id) throws BusinessException {
    checkIfBrandDoesNotExistsById(id);
    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfBrandExistsByName(String name) throws BusinessException {

    if (this.brandDao.existsByBrandName(name.toUpperCase())) {

      throw new BusinessException(BusinessMessages.BRANDALREADYEXISTS);
    }
  }

  private void checkIfBrandDoesNotExistsById(int id) throws BusinessException {

    if (!this.brandDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.BRANDNOTFOUND);
    }
  }
}

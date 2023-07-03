package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CityService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CityDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCityRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCityRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCityRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CityDao;
import com.turkcell.rentacar.entities.concretes.City;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CityManager implements CityService {

  private final CityDao cityDao;
  private final ModelMapperService modelMapperService;

  public CityManager(CityDao cityDao, ModelMapperService modelMapperService) {
    this.cityDao = cityDao;
    this.modelMapperService = modelMapperService;
  }

  @Override
  public DataResult<List<CityDto>> getAll() {

    List<City> result = this.cityDao.findAll();
    List<CityDto> response =
        result.stream()
            .map(city -> this.modelMapperService.forDto().map(city, CityDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateCityRequest createCityRequest) throws BusinessException {


    checkIfCityExistsByCityName(createCityRequest);

    City city = this.modelMapperService.forRequest().map(createCityRequest, City.class);

    this.cityDao.save(city);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<CityDto> getById(int id) throws BusinessException {

    checkIfCityDoesNotExistsById(id);
    //TODO : replace getByID
    City city = this.cityDao.getById(id);
    CityDto map = this.modelMapperService.forDto().map(city, CityDto.class);

    return new SuccessDataResult<>(map, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateCityRequest updateCityRequest) throws BusinessException {


    checkIfCityDoesNotExistsById(updateCityRequest.cityId());

    City city = this.modelMapperService.forRequest().map(updateCityRequest, City.class);
    city.setCityName(city.getCityName().toUpperCase());
    this.cityDao.save(city);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteCityRequest deleteCityRequest) throws BusinessException {

    checkIfCityDoesNotExistsById(deleteCityRequest.cityId());

    this.cityDao.deleteById(deleteCityRequest.cityId());

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  public Result checkIfCityDoesNotExistsByIdIsSuccess(int id) throws BusinessException {

    checkIfCityDoesNotExistsById(id);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfCityDoesNotExistsById(int id) throws BusinessException {

    if (!this.cityDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.CITYNOTFOUND);
    }
  }

  private void checkIfCityExistsByCityName(CreateCityRequest createCityRequest)
      throws BusinessException {
    if (this.cityDao.existsByCityNameIgnoreCase(createCityRequest.cityName())) {

      throw new BusinessException(BusinessMessages.CITYEXISTS);
    }
  }
}

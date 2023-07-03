package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.CorporateCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.CorporateCustomerDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteCorporateCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateCorporateCustomerRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.CorporateCustomerDao;
import com.turkcell.rentacar.entities.concretes.CorporateCustomer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class CorporateCustomerManager implements CorporateCustomerService {

  private final CorporateCustomerDao corporateCustomerDao;
  private final ModelMapperService modelMapperService;

  public CorporateCustomerManager(
      CorporateCustomerDao corporateCustomerDao, ModelMapperService modelMapperService) {

    this.corporateCustomerDao = corporateCustomerDao;
    this.modelMapperService = modelMapperService;
  }

  @Override
  public DataResult<List<CorporateCustomerDto>> getAll() {

    List<CorporateCustomer> result = this.corporateCustomerDao.findAll();
    List<CorporateCustomerDto> response =
        result.stream()
            .map(
                corporateCustomer ->
                    this.modelMapperService
                        .forDto()
                        .map(corporateCustomer, CorporateCustomerDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateCorporateCustomerRequest createCorporateCustomerRequest)
      throws BusinessException {


    checkIfCorporateCustomerEmailIsAvailable(createCorporateCustomerRequest.email());

    CorporateCustomer result =
        this.modelMapperService
            .forRequest()
            .map(createCorporateCustomerRequest, CorporateCustomer.class);
    result.setCompanyName(result.getCompanyName().toUpperCase());
    this.corporateCustomerDao.save(result);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<CorporateCustomerDto> getById(int id) throws BusinessException {

    checkIfCorporateCustomerDoesNotExistById(id);
		//TODO : replace getById
    CorporateCustomer result = this.corporateCustomerDao.getById(id);
    CorporateCustomerDto response =
        this.modelMapperService.forDto().map(result, CorporateCustomerDto.class);

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateCorporateCustomerRequest updateCorporateCustomerRequest)
      throws BusinessException {


    checkIfCorporateCustomerDoesNotExistById(updateCorporateCustomerRequest.customerId());

    CorporateCustomer result =
        this.modelMapperService
            .forRequest()
            .map(updateCorporateCustomerRequest, CorporateCustomer.class);
    result.setUserId(updateCorporateCustomerRequest.customerId());
		result.setCompanyName(result.getCompanyName().toUpperCase());
    this.corporateCustomerDao.save(result);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteCorporateCustomerRequest deleteCorporateCustomerRequest)
      throws BusinessException {

    checkIfCorporateCustomerDoesNotExistById(deleteCorporateCustomerRequest.customerId());

    this.corporateCustomerDao.deleteById(deleteCorporateCustomerRequest.customerId());

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  public Result checkIfCorporateCustomerDoesNotExistsByIdIsSucces(int id) throws BusinessException {

    checkIfCorporateCustomerDoesNotExistById(id);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  @Override
  public CorporateCustomer getByIdCorporateCustomer(int id) {

    return this.corporateCustomerDao.getById(id);
  }

  private void checkIfCorporateCustomerDoesNotExistById(int id) throws BusinessException {

    if (!this.corporateCustomerDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.CORPORATECUSTOMERNOTFOUND);
    }
  }

  private void checkIfCorporateCustomerEmailIsAvailable(String email) throws BusinessException {

    if (this.corporateCustomerDao.existsByEmail(email)) {

      throw new BusinessException(BusinessMessages.EMAILUSED);
    }
  }

  private void checkIfCorporateCustomerCompanyNameIsAvailable(String companyName)
      throws BusinessException {

    if (this.corporateCustomerDao.existsByCompanyName(companyName)) {

      throw new BusinessException(BusinessMessages.EMAILUSED);
    }
  }

  private void checkIfCorporateCustomerTaxNumberIsAvailable(String taxNumber)
      throws BusinessException {

    if (this.corporateCustomerDao.existsByTaxNumber(taxNumber)) {

      throw new BusinessException(BusinessMessages.EMAILUSED);
    }
  }
}

package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.IndividualCustomerService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.IndividualCustomerDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteIndividualCustomerRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateIndividualCustomerRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.IndividualCustomerDao;
import com.turkcell.rentacar.entities.concretes.IndividualCustomer;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;

@Service
public class IndividualCustomerManager implements IndividualCustomerService {

  private final IndividualCustomerDao individualCustomerDao;

  private final ModelMapperService modelMapperService;

  public IndividualCustomerManager(
      IndividualCustomerDao individualCustomerDao, ModelMapperService modelMapperService) {

    this.individualCustomerDao = individualCustomerDao;
    this.modelMapperService = modelMapperService;
  }

  @Override
  public SuccessDataResult<List<IndividualCustomerDto>> getAll() {

    List<IndividualCustomer> result = this.individualCustomerDao.findAll();
    List<IndividualCustomerDto> response =
        result.stream()
            .map(
                individualCustomer ->
                    this.modelMapperService
                        .forDto()
                        .map(individualCustomer, IndividualCustomerDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateIndividualCustomerRequest createIndividualCustomerRequest)
      throws BusinessException {

    checkIfIndividualCustomerEmailIsAvailable(createIndividualCustomerRequest.email());

    IndividualCustomer result =
        this.modelMapperService
            .forRequest()
            .map(createIndividualCustomerRequest, IndividualCustomer.class);
    result.setFirstName(result.getFirstName().toUpperCase());
    result.setLastName(result.getLastName().toUpperCase());
    this.individualCustomerDao.save(result);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<IndividualCustomerDto> getById(int id) throws BusinessException {

    checkIfIndividualCustomerDoesNotExistsById(id);
    // TODO : replace getById
    IndividualCustomer result = this.individualCustomerDao.getById(id);
    IndividualCustomerDto response =
        this.modelMapperService.forDto().map(result, IndividualCustomerDto.class);

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateIndividualCustomerRequest updateIndividualCustomerRequest)
      throws BusinessException {

    checkIfIndividualCustomerDoesNotExistsById(updateIndividualCustomerRequest.customerId());

    IndividualCustomer result =
        this.modelMapperService
            .forRequest()
            .map(updateIndividualCustomerRequest, IndividualCustomer.class);
    result.setUserId(updateIndividualCustomerRequest.customerId());

    result.setFirstName(result.getFirstName().toUpperCase());
    result.setLastName(result.getLastName().toUpperCase());

    this.individualCustomerDao.save(result);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteIndividualCustomerRequest deleteIndividualCustomerRequest)
      throws BusinessException {

    checkIfIndividualCustomerDoesNotExistsById(deleteIndividualCustomerRequest.customerId());

    this.individualCustomerDao.deleteById(deleteIndividualCustomerRequest.customerId());

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  public Result checkIfIndividualCustomerDoesNotExistsByIdIsSucces(int id)
      throws BusinessException {

    checkIfIndividualCustomerDoesNotExistsById(id);
    return new SuccessResult(ResultMessages.AVAILABLE);
  }

  private void checkIfIndividualCustomerDoesNotExistsById(int id) throws BusinessException {

    if (!this.individualCustomerDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.INDIVIDUALCUSTOMERNOTFOUND);
    }
  }

  private void checkIfIndividualCustomerEmailIsAvailable(String email) throws BusinessException {
    if (this.individualCustomerDao.existsByEmail(email)) {
      throw new BusinessException(BusinessMessages.EMAILUSED);
    }
  }
}

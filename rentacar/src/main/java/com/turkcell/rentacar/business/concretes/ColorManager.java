package com.turkcell.rentacar.business.concretes;

import com.turkcell.rentacar.business.abstracts.ColorService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
import com.turkcell.rentacar.business.dtos.ColorDto;
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
public class ColorManager implements ColorService {

  private final ColorDao colorDao;
  private final ModelMapperService modelMapperService;

  public ColorManager(ColorDao colorDao, ModelMapperService modelMapperService) {

    this.colorDao = colorDao;
    this.modelMapperService = modelMapperService;
  }

  @Override
  public DataResult<List<ColorDto>> getAll() {

    List<Color> result = this.colorDao.findAll();
    List<ColorDto> response =
        result.stream()
            .map(color -> this.modelMapperService.forDto().map(color, ColorDto.class))
            .collect(Collectors.toList());

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result add(CreateColorRequest createColorRequest) throws BusinessException {

    checkIfColorExistsByName(createColorRequest.colorName());

    Color color = modelMapperService.forRequest().map(createColorRequest, Color.class);
    color.setColorName(color.getColorName().toUpperCase());
    this.colorDao.save(color);

    return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
  }

  @Override
  public DataResult<ColorDto> getById(int id) throws BusinessException {

    checkIfColorDoesNotExistsById(id);
    // TODO : Replace getById
    Color result = this.colorDao.getById(id);
    ColorDto response = this.modelMapperService.forDto().map(result, ColorDto.class);

    return new SuccessDataResult<>(response, ResultMessages.LISTEDSUCCESSFUL);
  }

  @Override
  public Result update(UpdateColorRequest updateColorRequest) throws BusinessException {

    checkIfColorDoesNotExistsById(updateColorRequest.colorId());

    Color color = this.modelMapperService.forRequest().map(updateColorRequest, Color.class);
    color.setColorName(color.getColorName().toUpperCase());
    this.colorDao.save(color);

    return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
  }

  @Override
  public Result delete(DeleteColorRequest deleteColorRequest) throws BusinessException {

    checkIfColorDoesNotExistsById(deleteColorRequest.colorId());

    Color color = this.modelMapperService.forRequest().map(deleteColorRequest, Color.class);
    this.colorDao.delete(color);

    return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
  }

  private void checkIfColorExistsByName(String name) throws BusinessException {

    if (this.colorDao.existsByColorNameIgnoreCase(name)) {

      throw new BusinessException(BusinessMessages.COLOREXISTS);
    }
  }

  private void checkIfColorDoesNotExistsById(int id) throws BusinessException {

    if (!this.colorDao.existsById(id)) {

      throw new BusinessException(BusinessMessages.COLORNOTFOUND);
    }
  }

  public Result checkIfColorDoesNotExists(int id) throws BusinessException {

    checkIfColorDoesNotExistsById(id);

    return new SuccessResult(ResultMessages.AVAILABLE);
  }
}

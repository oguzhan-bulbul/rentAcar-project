package com.turkcell.rentacar.api.controllers;

import com.turkcell.rentacar.business.abstracts.BrandService;
import com.turkcell.rentacar.business.dtos.BrandDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateBrandRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteBrandRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateBrandRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import jakarta.validation.Valid;
import java.util.List;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/brands")
public class BrandsController {

  private final BrandService brandService;

  public BrandsController(BrandService brandService) {

    this.brandService = brandService;
  }

  @GetMapping("/getall")
  public DataResult<List<BrandListDto>> getAll() {

    return this.brandService.getAll();
  }

  @PostMapping("/save")
  public Result add(@RequestBody @Valid CreateBrandRequest createBrandRequest)
      throws BusinessException {

    return this.brandService.add(createBrandRequest);
  }

  @GetMapping("/get")
  public DataResult<BrandDto> get(@RequestParam int id) throws BusinessException {

    return this.brandService.getById(id);
  }

  @PutMapping("/update")
  public Result update(@RequestBody @Valid UpdateBrandRequest updateBrandRequest)
      throws BusinessException {

    return this.brandService.update(updateBrandRequest);
  }

  @DeleteMapping("/delete")
  public Result delete(@RequestBody @Valid DeleteBrandRequest deleteBrandRequest)
      throws BusinessException {

    return this.brandService.delete(deleteBrandRequest);
  }
}

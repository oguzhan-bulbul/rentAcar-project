package com.turkcell.rentacar.business.abstracts;

import com.turkcell.rentacar.business.dtos.UserDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteUserRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import java.util.List;

public interface UserService {
	
	DataResult<List<UserDto>> getAll();
	
	Result add(CreateUserRequest createUserRequest) throws BusinessException;
	
	DataResult<UserDto> getById(int id) throws BusinessException;
	
	Result update(UpdateUserRequest updateUserRequest) throws BusinessException;
	
	Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException;	
	
}

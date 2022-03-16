package com.turkcell.rentacar.business.concretes;

import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.dtos.UserDto;
import com.turkcell.rentacar.business.dtos.UserListDto;
import com.turkcell.rentacar.business.requests.createRequests.CreateUserRequest;
import com.turkcell.rentacar.business.requests.deleteRequests.DeleteUserRequest;
import com.turkcell.rentacar.business.requests.updateRequests.UpdateUserRequest;
import com.turkcell.rentacar.core.entities.User;
import com.turkcell.rentacar.core.utilities.exceptions.BusinessException;
import com.turkcell.rentacar.core.utilities.mapping.ModelMapperService;
import com.turkcell.rentacar.core.utilities.results.DataResult;
import com.turkcell.rentacar.core.utilities.results.Result;
import com.turkcell.rentacar.core.utilities.results.SuccessDataResult;
import com.turkcell.rentacar.core.utilities.results.SuccessResult;
import com.turkcell.rentacar.dataAccess.abstracts.UserDao;

public class UserManager implements UserService{
	
	private final UserDao userDao;
	private final ModelMapperService modelMapperService;
	
	@Autowired
	public UserManager(UserDao userDao, ModelMapperService modelMapperService) {
		this.userDao = userDao;
		this.modelMapperService = modelMapperService;
	}

	@Override
	public DataResult<List<UserListDto>> getAll() {
		
		List<User> result = this.userDao.findAll();
		
		List<UserListDto> response = result.stream().map(user -> this.modelMapperService.forDto().map(user, UserListDto.class))
				.collect(Collectors.toList());
		
		return new SuccessDataResult<List<UserListDto>>(response,"Users listed");
	}

	@Override
	public Result add(CreateUserRequest createUserRequest) throws BusinessException {
		
		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);	
		this.userDao.save(user);
		
		return new SuccessResult("User saved");
	}

	@Override
	public DataResult<UserDto> getById(int id) throws BusinessException {
		
		checkIfUserDoesNotExistsById(id);
		
		User user = this.userDao.getById(id);
		UserDto response = this.modelMapperService.forDto().map(user, UserDto.class);
		
		return new SuccessDataResult<UserDto>(response,"User listed");
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) throws BusinessException {
		
		checkIfUserDoesNotExistsById(updateUserRequest.getUserId());
		
		User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);
		this.userDao.save(user);
		
		return new SuccessResult("User updated");
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {
		
		checkIfUserDoesNotExistsById(deleteUserRequest.getUserId());
		
		this.userDao.deleteById(deleteUserRequest.getUserId());
		
		return new SuccessResult("User deleted");
	}
	
	private void checkIfUserDoesNotExistsById(int id) throws BusinessException {
		
		if(!this.userDao.existsById(id)) {
			
			throw new BusinessException("User does not exists.");
			
		}
	}

}

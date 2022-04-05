package com.turkcell.rentacar.business.concretes;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.turkcell.rentacar.business.abstracts.UserService;
import com.turkcell.rentacar.business.constants.messages.BusinessMessages;
import com.turkcell.rentacar.business.constants.messages.ResultMessages;
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

@Service
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
		
		return new SuccessDataResult<List<UserListDto>>(response,ResultMessages.LISTEDSUCCESSFUL);
	}
	
	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		User user = this.userDao.getByEmail(username);
		
		if(user == null) {
			throw new UsernameNotFoundException(BusinessMessages.USERNOTFOUND);
		}
		
		return new org.springframework.security.core.userdetails.User(user.getEmail(), user.getEncryptedPassword(), true, true, true, true, new ArrayList<>());
	}


	@Override
	public Result add(CreateUserRequest createUserRequest) throws BusinessException {
		
		User user = this.modelMapperService.forRequest().map(createUserRequest, User.class);	
		this.userDao.save(user);
		
		return new SuccessResult(ResultMessages.ADDEDSUCCESSFUL);
	}

	@Override
	public DataResult<UserDto> getById(String id) throws BusinessException {
		
		checkIfUserDoesNotExistsById(id);
		
		User user = this.userDao.getById(id);
		UserDto response = this.modelMapperService.forDto().map(user, UserDto.class);
		
		return new SuccessDataResult<UserDto>(response,ResultMessages.LISTEDSUCCESSFUL);
	}

	@Override
	public Result update(UpdateUserRequest updateUserRequest) throws BusinessException {
		
		checkIfUserDoesNotExistsById(updateUserRequest.getUserId());
		
		User user = this.modelMapperService.forRequest().map(updateUserRequest, User.class);
		this.userDao.save(user);
		
		return new SuccessResult(ResultMessages.UPDATESUCCESSFUL);
	}

	@Override
	public Result delete(DeleteUserRequest deleteUserRequest) throws BusinessException {
		
		checkIfUserDoesNotExistsById(deleteUserRequest.getUserId());
		
		this.userDao.deleteById(deleteUserRequest.getUserId());
		
		return new SuccessResult(ResultMessages.DELETESUCCESSFUL);
	}
	
	private void checkIfUserDoesNotExistsById(String id) throws BusinessException {
		
		if(!this.userDao.existsById(id)) {
			
			throw new BusinessException(BusinessMessages.USERNOTFOUND);
			
		}
	}
	
	private void checkIfUserDoesNotExistsByEmail(String email) throws BusinessException {
		if(!this.userDao.existsByEmail(email)) {
			throw new BusinessException(BusinessMessages.USERNOTFOUND);
		}
	}

	@Override
	public UserDto getUserByDetailsByEmail(String email) throws BusinessException {
		
		checkIfUserDoesNotExistsByEmail(email);
		
		User user = this.userDao.getByEmail(email);
		
		UserDto userDetails = this.modelMapperService.forDto().map(user, UserDto.class);
		return userDetails;
	}


}

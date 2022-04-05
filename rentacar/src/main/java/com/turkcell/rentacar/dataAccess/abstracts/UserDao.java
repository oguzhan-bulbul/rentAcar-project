package com.turkcell.rentacar.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.rentacar.core.entities.User;

public interface UserDao extends JpaRepository<User, String>{
	
	boolean existsByEmail(String email);
	
	User getByEmail(String email);
	

}

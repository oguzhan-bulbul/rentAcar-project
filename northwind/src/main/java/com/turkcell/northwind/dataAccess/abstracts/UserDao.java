package com.turkcell.northwind.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;

import com.turkcell.northwind.entities.concretes.UserEntity;

public interface UserDao extends JpaRepository<UserEntity, String>{
	
	UserEntity findByEmail(String email);

}

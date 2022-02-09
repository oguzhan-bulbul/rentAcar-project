package com.turkcell.northwind.dataAccess.abstracts;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.turkcell.northwind.entities.concretes.Product;
@Repository
public interface ProductDao extends JpaRepository<Product, Integer>{

}

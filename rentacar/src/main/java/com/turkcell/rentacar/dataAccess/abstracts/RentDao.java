package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.Rent;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RentDao extends JpaRepository<Rent, Integer> {

  List<Rent> getAllByCar_CarId(int carid);

  boolean existsByOrderedAdditionalServices_OrderedAdditionalServiceId(int id);
}

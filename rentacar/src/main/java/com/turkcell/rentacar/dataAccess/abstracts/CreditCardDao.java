package com.turkcell.rentacar.dataAccess.abstracts;

import com.turkcell.rentacar.entities.concretes.CreditCard;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface CreditCardDao extends JpaRepository<CreditCard, Integer> {

  List<CreditCard> getAllByCustomer_CustomerId(int id);
}

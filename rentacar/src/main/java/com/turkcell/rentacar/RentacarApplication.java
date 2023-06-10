package com.turkcell.rentacar;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement(proxyTargetClass = true)
@EntityScan("com.turkcell.rentacar.entities")
@EnableAspectJAutoProxy(proxyTargetClass = true)
public class RentacarApplication {

  public static void main(String[] args) {
    SpringApplication.run(RentacarApplication.class, args);
  }
}

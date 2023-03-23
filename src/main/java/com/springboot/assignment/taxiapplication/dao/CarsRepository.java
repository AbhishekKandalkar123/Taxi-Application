package com.springboot.assignment.taxiapplication.dao;

import com.springboot.assignment.taxiapplication.model.Car;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface CarsRepository extends JpaRepository<Car, String>, JpaSpecificationExecutor<Car> {

}

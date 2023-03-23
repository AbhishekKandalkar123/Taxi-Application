package com.springboot.assignment.taxiapplication.dao;

import com.springboot.assignment.taxiapplication.model.Driver;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DriverRepository extends JpaRepository<Driver, Integer> {

}


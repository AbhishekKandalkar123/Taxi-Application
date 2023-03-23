package com.springboot.assignment.taxiapplication.dao;

import com.springboot.assignment.taxiapplication.model.SelectedCars;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface SelectedCarsRepository extends JpaRepository<SelectedCars, Integer> {
    @Query("SELECT c.id FROM SelectedCars c WHERE c.driverId=:driverId and c.carId in :carIds")
    public List<Integer> findByDriverIdAndCarIdIn(@Param("driverId") int driverId, @Param("carIds") List<String> carIds);
}
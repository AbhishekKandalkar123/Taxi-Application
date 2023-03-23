package com.springboot.assignment.taxiapplication.service;

import com.springboot.assignment.taxiapplication.model.Car;
import org.springframework.http.ResponseEntity;
import java.util.List;

public interface CarsService {
    public String addCars(List<Car> cars);
    public String getAllCars();
    public ResponseEntity<?> getCarById(String license_plate) throws Exception;
    public String delete(String license_plate);
    public String update(String license_plate, Car carBody);
    public List<Car> searchCars(String filters);
}

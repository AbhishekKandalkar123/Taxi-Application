package com.springboot.assignment.taxiapplication.controller;

import com.springboot.assignment.taxiapplication.model.Car;
import com.springboot.assignment.taxiapplication.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/car")
public class CarController{

    @Autowired
    private CarsService carsService;

    @PostMapping
    public String addCars(@RequestBody List<Car> cars){
        return carsService.addCars(cars);
    }

    @GetMapping
    @ResponseBody
    public String getAllCars(){
        return carsService.getAllCars();
    }

    @GetMapping("/{license_plate}")
    @ResponseBody
    public ResponseEntity<?> getCarById(@PathVariable String license_plate) throws Exception{
        return carsService.getCarById(license_plate);
    }

    @DeleteMapping("/{license_plate}")
    public String delete(@PathVariable String license_plate){
        return carsService.delete(license_plate);
    }

    @PutMapping("/{license_plate}")
    public String update(@PathVariable String license_plate, @RequestBody Car carBody){
       return carsService.update(license_plate, carBody);
    }

    @GetMapping("/filter")
    public List<Car> searchCars(@RequestParam String filters){
        return carsService.searchCars(filters);
    }
}
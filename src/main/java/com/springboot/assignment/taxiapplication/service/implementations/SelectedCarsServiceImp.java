package com.springboot.assignment.taxiapplication.service.implementations;

import com.springboot.assignment.taxiapplication.dao.CarsRepository;
import com.springboot.assignment.taxiapplication.dao.DriverRepository;
import com.springboot.assignment.taxiapplication.dao.SelectedCarsRepository;
import com.springboot.assignment.taxiapplication.model.Car;
import com.springboot.assignment.taxiapplication.model.Driver;
import com.springboot.assignment.taxiapplication.model.SelectedCars;
import com.springboot.assignment.taxiapplication.service.SelectedCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class SelectedCarsServiceImp implements SelectedCarsService {
    @Autowired
    private SelectedCarsRepository selectedCarsRepository;
    @Autowired
    private DriverRepository driverRepository;
    @Autowired
    private CarsRepository carsRepository;

    @Override
    public String selectCars(int driverId, List<String> carIds) {
        SelectedCars selectedCars = null;
        List<String> invalidCarIDs = new ArrayList<>();
        List<String> validCarIds = new ArrayList<>();

        Optional<Driver> driver = driverRepository.findById(driverId);
        if(driver.isEmpty())
            return new Exception("Driver Not Found!").getMessage();

        for(String carId : carIds){
            Optional<Car> car = carsRepository.findById(carId);
            if(car.isEmpty()){
                invalidCarIDs.add(carId);
                continue;
            }
            validCarIds.add(carId);
            selectedCars = new SelectedCars(driverId, carId);
            selectedCarsRepository.save(selectedCars);
        }
        if(invalidCarIDs.isEmpty()){
            return "Valid CarIds: "+ validCarIds;
        }
        return "Invalid CarIds: " + invalidCarIDs +"\n" +"Valid CarIds: "+ validCarIds;
    }

    @Override
    public String deSelectCars(int driverId, List<String> carIds) {
        Optional<Driver> driver = driverRepository.findById(driverId);
        if(driver.isEmpty())
            return new Exception("Driver Not Found!").getMessage();

        List<Integer> validIds = selectedCarsRepository.findByDriverIdAndCarIdIn(driverId, carIds);
        if(validIds.isEmpty()) {
            return new Exception("No Valid IDs!").getMessage();
        }
        selectedCarsRepository.deleteAllById(validIds);
        return "Valid CarIds: " + validIds + "  Are Unselected";
    }

}
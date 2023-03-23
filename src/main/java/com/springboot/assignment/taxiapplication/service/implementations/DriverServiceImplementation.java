package com.springboot.assignment.taxiapplication.service.implementations;

import com.springboot.assignment.taxiapplication.dao.CarsRepository;
import com.springboot.assignment.taxiapplication.dao.DriverRepository;
import com.springboot.assignment.taxiapplication.exceptions.IdNotFoundException;
import com.springboot.assignment.taxiapplication.model.Driver;
import com.springboot.assignment.taxiapplication.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DriverServiceImplementation implements DriverService {
    @Autowired
    private DriverRepository driverRepository;
    private CarsRepository carsRepository;

    @Override
    public String addDrivers(List<Driver> drivers) {
        List<Driver> invalidDrivers = new ArrayList<>();
        List<Driver> validDrivers = new ArrayList<>();

        try{
            if(!Objects.isNull(drivers)) {
                for (Driver driver : drivers) {
                    int id = driver.getDriverId();
                    String name = driver.getName();
                    int isFourWheelerLicensed = driver.getIsFourWheelerLicensed();
                    int experience = driver.getExperience();

                    if (id <= 0){
                        invalidDrivers.add(driver);
                        continue;
                    }
                    if (name == null || name.isEmpty()) {
                        invalidDrivers.add(driver);
                        continue;
                    }
                    if (experience<0) {
                        invalidDrivers.add(driver);
                        continue;
                    }
                    if(isFourWheelerLicensed != 1){
                        invalidDrivers.add(driver);
                        continue;
                    }
                    validDrivers.add(driver);
                }
                driverRepository.saveAll(validDrivers);
            }
        } catch (Exception ignored){
        }
        if(invalidDrivers.size()==0)
            return "Valid Drivers: \n"+validDrivers;
        return  "Invalid Drivers: \n"+ invalidDrivers +"\n"+"Valid Drivers: \n" + validDrivers;
    }

    @Override
    public String getAllDrivers() {
        List<Driver> drivers =  driverRepository.findAll();
        if(drivers.isEmpty()){
            return new Exception("Data Not Found!").getMessage();
        }
        return "All Drivers: \n"+drivers;
    }

    @Override
    public String delete(int id) {
        Optional<Driver> driver = driverRepository.findById(id);
        if(driver.isPresent()) {
            driverRepository.deleteById(id);
            return "Deleted " + id;
        }
        return id+" Not Found";
    }

    @Override
    public String update( int id, Driver driverBody) {
        Optional<Driver> driver = driverRepository.findById(id);
        Driver driver1 = driver.orElse(null);
        if(driver1!=null) {
            driver1.setName(driverBody.getName());
            driver1.setExperience(driverBody.getExperience());
            driver1.setIsFourWheelerLicensed(driverBody.getIsFourWheelerLicensed());

            return "Updated " + driverRepository.save(driver1);
        }
        return new IdNotFoundException("License Plate Not Found!").getMessage();
    }

}

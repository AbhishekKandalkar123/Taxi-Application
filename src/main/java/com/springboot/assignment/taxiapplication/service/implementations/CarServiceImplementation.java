package com.springboot.assignment.taxiapplication.service.implementations;

import com.springboot.assignment.taxiapplication.dao.CarsRepository;
import com.springboot.assignment.taxiapplication.exceptions.IdNotFoundException;
import com.springboot.assignment.taxiapplication.model.Car;
import com.springboot.assignment.taxiapplication.model.EngineType;
import com.springboot.assignment.taxiapplication.model.search.CarSpecificationBuilder;
import com.springboot.assignment.taxiapplication.service.CarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
public class CarServiceImplementation implements CarsService {

    @Autowired
    private CarsRepository carsRepository;

    @Override
    public String addCars(List<Car> cars) {
        List<Car> invalidCars = new ArrayList<>();
        List<Car> validCars = new ArrayList<>();
        try{
            if(!Objects.isNull(cars)) {
                for (Car car : cars) {
                    String id = car.getLicensePlate();
                    String manufacturer = car.getManufacturer();
                    int rating = car.getRating();
                    int seatCount = car.getSeatCount();
                    int convertible = car.getConvertible();
                    Enum<EngineType> engineType = EngineType.getEngineType(car.getEngineType());

                    if (id == null || id.isEmpty()) {
                        invalidCars.add(car);
                        continue;
                    }
                    if (rating < 0 || rating > 5) {
                        invalidCars.add(car);
                        continue;
                    }
                    if (seatCount <= 0) {
                        invalidCars.add(car);
                        continue;
                    }
                    if (manufacturer == null || manufacturer.isEmpty()) {
                        invalidCars.add(car);
                        continue;
                    }
                    if(convertible<0 || convertible>1){
                        invalidCars.add(car);
                        continue;
                    }
                    if (engineType == null) {
                        invalidCars.add(car);
                        continue;
                    }
                    validCars.add(car);
                }
                carsRepository.saveAll(validCars);
            }
        } catch (Exception ignored){
        }
        if(invalidCars.size()==0)
            return "Valid Cars: \n"+validCars;
        return  "Invalid Cars: \n"+invalidCars +"\n"+"Valid Cars: \n" + validCars;
    }

    @Override
    public String getAllCars(){
        List<Car> cars = carsRepository.findAll();
        if(cars.isEmpty()){
            return new Exception("Data Not Found!").getMessage();
        }
        return"All Cars: \n"+cars;
    }

    @Override
    public ResponseEntity<?> getCarById(String licensePlate) throws Exception {
        Optional<Car> byId = carsRepository.findById(licensePlate);
        if(byId.isPresent())
            return new ResponseEntity<>(byId.get(), HttpStatus.OK);
        else {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @Override
    public String delete(String licensePlate) {
        Optional<Car> car= carsRepository.findById(licensePlate);
        if(car.isPresent()) {
            carsRepository.deleteById(licensePlate);
            return "Deleted " + licensePlate;
        }
        return licensePlate+" Not Found";
    }

    @Override
    public String update(String licensePlate, Car carBody) {
        Optional<Car> car= carsRepository.findById(licensePlate);
        Car car1 = car.orElse(null);
        if(car1!=null) {
            car1.setEngineType(carBody.getEngineType());
            car1.setConvertible(carBody.getConvertible());
            car1.setRating(carBody.getRating());
            car1.setSeatCount(carBody.getSeatCount());
            car1.setManufacturer(carBody.getManufacturer());

            return "Updated " + carsRepository.save(car1);
        }
        return new IdNotFoundException("License Plate Not Found!").getMessage();
    }

    @Override
    public List<Car> searchCars(String filters) {
        CarSpecificationBuilder carSpecificationBuilder = new CarSpecificationBuilder();
        Pattern pattern = Pattern.compile("(\\w+)\\s+(eq|ne)\\s+(\\w+)");
        Matcher matcher = pattern.matcher(filters);
        while(matcher.find()){
            carSpecificationBuilder.with(matcher.group(1), matcher.group(2), matcher.group(3));
        }

        Specification<Car> car = carSpecificationBuilder.build();
        List<Car> c = carsRepository.findAll(car);
        return c;
    }
}
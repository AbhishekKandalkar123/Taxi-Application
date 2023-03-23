package com.springboot.assignment.taxiapplication.controller;

import com.springboot.assignment.taxiapplication.model.Driver;
import com.springboot.assignment.taxiapplication.service.DriverService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/driver")
public class DriverController {
    @Autowired
    private DriverService driverService;

    @PostMapping
    public String addDrivers(@RequestBody List<Driver> drivers){
        return driverService.addDrivers(drivers);
    }

    @GetMapping
    public String getAllDrivers() {
        return driverService.getAllDrivers();
    }

    @DeleteMapping("/{driverId}")
    public String delete(@PathVariable int driverId){
        return driverService.delete(driverId);
    }

    @PutMapping("/{driverId}")
    public String update(@PathVariable int driverId, @RequestBody Driver driverBody) {
        return driverService.update(driverId, driverBody);
    }
}

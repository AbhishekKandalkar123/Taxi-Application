package com.springboot.assignment.taxiapplication.controller;

import com.springboot.assignment.taxiapplication.service.SelectedCarsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@RestController
@RequestMapping("/select_cars")
public class SelectedCarsController {
    @Autowired
    private SelectedCarsService selectedCarsService;

    @PostMapping("/{driverId}")
    public String selectCars(@PathVariable int driverId, @RequestBody List<String> carIds) {
        return selectedCarsService.selectCars(driverId, carIds);
    }

    @DeleteMapping("/{driverId}")
    public String deSelectCars(@PathVariable int driverId, @RequestBody List<String> carIds) {
        return selectedCarsService.deSelectCars(driverId, carIds);
    }
}

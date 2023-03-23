package com.springboot.assignment.taxiapplication.service;

import java.util.List;

public interface SelectedCarsService {
    public String selectCars(int driverId, List<String> carIds);
    public String deSelectCars(int driverId, List<String> carIds);
}

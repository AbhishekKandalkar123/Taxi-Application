package com.springboot.assignment.taxiapplication.service;

import com.springboot.assignment.taxiapplication.exceptions.IdNotFoundException;
import com.springboot.assignment.taxiapplication.model.Driver;
import java.util.List;

public interface DriverService {
    public String addDrivers(List<Driver> drivers);
    public String getAllDrivers();
    public String delete(int id);
    public String update(int id, Driver driverBody);
}

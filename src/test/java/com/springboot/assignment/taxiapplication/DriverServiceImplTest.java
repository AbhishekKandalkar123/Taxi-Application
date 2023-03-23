package com.springboot.assignment.taxiapplication;

import com.springboot.assignment.taxiapplication.dao.DriverRepository;
import com.springboot.assignment.taxiapplication.model.Driver;
import com.springboot.assignment.taxiapplication.service.implementations.DriverServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class DriverServiceImplTest {
    @Mock
    DriverRepository driverRepository;

    @InjectMocks
    DriverServiceImplementation driverServiceImplementation;

    @Test
    void shouldAddDriversBasicTest(){
        List<Driver> driverList = new ArrayList<>();
        driverList.add(new Driver(1,"Ajay", 1, 2));
        driverList.add(new Driver(2,"Vijay", 1, 3));
        String expectedResult ="Valid Drivers: \n" +
                "[Driver(driverId=1, name=Ajay, isFourWheelerLicensed=true, experience=2), " +
                "Driver(driverId=2, name=Vijay, isFourWheelerLicensed=true, experience=3)]";
        String actual = driverServiceImplementation.addDrivers(driverList);
        assertEquals(expectedResult, actual);
    }

    @Test
    void shouldAddToInvalidDriverListForInvalidEntry(){
        List<Driver> driverList = new ArrayList<>();
        // Id<=0
        driverList.add(new Driver(-1,"Ajay", 1, 2));
        driverList.add(new Driver(0,"Ajay", 1, 2));

        // name == null || name.isEmpty()
        driverList.add(new Driver(1,"", 1, 2));
        driverList.add(new Driver(1,null, 1, 2));

        // experience < 0
        driverList.add(new Driver(1,"Ajay", 1, -1));

        // isFourWheelerLicensed!=1
        driverList.add(new Driver(1,"Ajay", 0, 2));

        String expectedResult = "Invalid Drivers: \n" +
                "[Driver(driverId=-1, name=Ajay, isFourWheelerLicensed=true, experience=2), " +
                "Driver(driverId=0, name=Ajay, isFourWheelerLicensed=true, experience=2), " +
                "Driver(driverId=1, name=, isFourWheelerLicensed=true, experience=2), " +
                "Driver(driverId=1, name=null, isFourWheelerLicensed=true, experience=2), " +
                "Driver(driverId=1, name=Ajay, isFourWheelerLicensed=true, experience=-1), " +
                "Driver(driverId=1, name=Ajay, isFourWheelerLicensed=false, experience=2)]\n" +
                "Valid Drivers: \n" +
                "[]";
        String actual = driverServiceImplementation.addDrivers(driverList);
        assertEquals(expectedResult, actual);
    }

    @Test
    void shouldReturnAllDrivers(){
        List<Driver> driverList = new ArrayList<>();
        driverList.add(new Driver(1,"Ajay", 1, 2));
        driverList.add(new Driver(2,"Vijay", 1, 3));
        when(driverRepository.findAll()).thenReturn(driverList);
        String actual = driverServiceImplementation.getAllDrivers();
        assertEquals("All Drivers: \n"+driverList, actual);
    }

    @Test
    void shouldNotReturnDriversForEmptyList()  {
        List<Driver> driverList = new ArrayList<>();
        when(driverRepository.findAll()).thenReturn(driverList);
        String actual = driverServiceImplementation.getAllDrivers();
        assertEquals("Data Not Found!", actual);
    }

    @Test
    void shouldDeleteDriver(){
        Driver driver = new Driver(1,"Ajay", 1, 2);
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        String actual = driverServiceImplementation.delete(1);
        assertEquals("Deleted " + 1, actual);
    }

    @Test
    void shouldNotDeleteCarIfCarNotFound(){
        Driver driver = new Driver(1,"Ajay", 1, 2);
        String actual = driverServiceImplementation.delete(2);
        assertEquals(2 + " Not Found", actual);
    }

    @Test
    void shouldUpdateDriver(){
        Driver driver = new Driver(1,"Ajay", 1, 2);
        Driver driverBody = new Driver(1,"Vijay", 1, 4);
        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(driverRepository.save(driver)).thenReturn(driver);
        String actual = driverServiceImplementation.update(1, driverBody);
        assertEquals("Updated " + driver, actual);
    }

}

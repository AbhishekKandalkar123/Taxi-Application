package com.springboot.assignment.taxiapplication;

import com.springboot.assignment.taxiapplication.dao.CarsRepository;
import com.springboot.assignment.taxiapplication.dao.DriverRepository;
import com.springboot.assignment.taxiapplication.dao.SelectedCarsRepository;
import com.springboot.assignment.taxiapplication.model.Car;
import com.springboot.assignment.taxiapplication.model.Driver;
import com.springboot.assignment.taxiapplication.model.SelectedCars;
import com.springboot.assignment.taxiapplication.service.implementations.SelectedCarsServiceImp;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class SelectedCarsServiceImplTest {

    @Mock
    private SelectedCarsRepository selectedCarsRepository;

    @Mock
    private DriverRepository driverRepository;

    @Mock
    private CarsRepository carsRepository;

    @InjectMocks
    private SelectedCarsServiceImp selectedCarsServiceImp;

    @Test
    void shouldSelectCars(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        Car car = new Car("MH12ND8943", 4, 0, 4,"Electric", "TATA");
        List<String> carIds = new ArrayList<>();
        carIds.add("MH12ND8943");
        SelectedCars selectedCars = new SelectedCars(driver.getDriverId(), carIds.get(0));

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(carsRepository.findById("MH12ND8943")).thenReturn(Optional.of(car));
        when(selectedCarsRepository.save(any(SelectedCars.class))).thenReturn(selectedCars);

        String actual = selectedCarsServiceImp.selectCars(driver.getDriverId(), carIds);
        assertEquals("Valid CarIds: "+ carIds, actual);
    }

    @Test
    void shouldNotSelectCarsIfDriverNotFound(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        Car car = new Car("MH12ND8943", 4, 0, 4,"Electric", "TATA");
        List<String> carIds = new ArrayList<>();
        carIds.add("MH12ND8943");
        SelectedCars selectedCars = new SelectedCars(driver.getDriverId(), carIds.get(0));

        String actual = selectedCarsServiceImp.selectCars(2, carIds);
        assertEquals("Driver Not Found!", actual);
    }

    @Test
    void shouldAddToInvalidCarsIfCarIsNotFound(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        Car car = new Car("MH12ND8943", 4, 0, 4,"Electric", "TATA");
        List<String> carIds = new ArrayList<>();
        carIds.add("MH13ND8943");
        SelectedCars selectedCars = new SelectedCars(driver.getDriverId(), carIds.get(0));

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        String actual = selectedCarsServiceImp.selectCars(driver.getDriverId(), carIds);
        assertEquals("Invalid CarIds: " + carIds +"\n" +"Valid CarIds: "+ "[]", actual);
    }

    @Test
    void shouldDeselectCars(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        List<String> carIds = new ArrayList<>();
        carIds.add("MH12ND8943");
        List<Integer> validIds = new ArrayList<>();
        validIds.add(driver.getDriverId());

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));
        when(selectedCarsRepository.findByDriverIdAndCarIdIn(driver.getDriverId(), carIds)).thenReturn(validIds);

        String actual = selectedCarsServiceImp.deSelectCars(driver.getDriverId(), carIds);
        assertEquals("Valid CarIds: " + validIds + "  Are Unselected", actual);
    }

    @Test
    void shouldNotDeselectCarsIfDriverNotFound(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        List<String> carIds = new ArrayList<>();
        carIds.add("MH12ND8943");

        String actual = selectedCarsServiceImp.deSelectCars(driver.getDriverId(), carIds);
        assertEquals("Driver Not Found!", actual);
    }

    @Test
    void shouldNotDeselectCarsIfInValidId(){
        Driver driver = new Driver(1, "Ajay", 1, 3);
        List<String> carIds = new ArrayList<>();
        carIds.add("MH12ND8943");
        List<Integer> validIds = new ArrayList<>();
        validIds.add(2);

        when(driverRepository.findById(1)).thenReturn(Optional.of(driver));

        String actual = selectedCarsServiceImp.deSelectCars(driver.getDriverId(), carIds);
        assertEquals("No Valid IDs!", actual);
    }
}
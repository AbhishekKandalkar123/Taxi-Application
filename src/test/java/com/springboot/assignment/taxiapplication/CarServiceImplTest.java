package com.springboot.assignment.taxiapplication;

import com.springboot.assignment.taxiapplication.dao.CarsRepository;
import com.springboot.assignment.taxiapplication.model.Car;
import com.springboot.assignment.taxiapplication.service.implementations.CarServiceImplementation;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class CarServiceImplTest {
    @Mock
    private CarsRepository carsRepository;

    @InjectMocks
    CarServiceImplementation carServiceImplementation;

    @Test
    void shouldAddCarsBasicTest(){
        List<Car> carList = new ArrayList<>();
        carList.add(new Car("MH12BF3743", 4,0,5,"Electric","TATA"));
        carList.add(new Car("MH11KS4623", 5,1,4,"Fuel","Maruti"));
        String expectedResult ="Valid Cars: \n" +
                "[Car(licensePlate=MH12BF3743, seatCount=4, convertible=0, rating=5, engineType=Electric, manufacturer=TATA), " +
                "Car(licensePlate=MH11KS4623, seatCount=5, convertible=1, rating=4, engineType=Fuel, manufacturer=Maruti)]";

        String actual = carServiceImplementation.addCars(carList);
        assertEquals(expectedResult, actual);
    }

    @Test
    void shouldAddToInvalidCarsListForInvalidEntry(){
        List<Car> carList = new ArrayList<>();
        // Id==null
        carList.add(new Car("", 4,0,5,"Electric","TATA"));
        // rating < 0
        carList.add(new Car("MH10CF3743", 4,0,-1,"Electric","TATA"));
        //rating > 5
        carList.add(new Car("MH11BH3743", 4,0,6,"Electric","TATA"));
        //seatCount <= 0
        carList.add(new Car("MH15TF3743", 0,0,5,"Electric","TATA"));
        //manufacturer == isEmpty
        carList.add(new Car("MH13BE3743", 4,0,5,"Electric",""));
        //engineType==isEmpty
        carList.add(new Car("MH14BA3743", 4,0,5,"","TATA"));
        //convertible < 0
        carList.add(new Car("MH14BK3743", 4,-1,5,"Gas","TATA"));
        //convertible > 1
        carList.add(new Car("MH14BS3743", 4,2,5,"Gas","TATA"));

        String expectedResult ="Invalid Cars: \n" +
                "[Car(licensePlate=, seatCount=4, convertible=0, rating=5, engineType=Electric, manufacturer=TATA), " +
                "Car(licensePlate=MH10CF3743, seatCount=4, convertible=0, rating=-1, engineType=Electric, manufacturer=TATA), " +
                "Car(licensePlate=MH11BH3743, seatCount=4, convertible=0, rating=6, engineType=Electric, manufacturer=TATA), " +
                "Car(licensePlate=MH15TF3743, seatCount=0, convertible=0, rating=5, engineType=Electric, manufacturer=TATA), " +
                "Car(licensePlate=MH13BE3743, seatCount=4, convertible=0, rating=5, engineType=Electric, manufacturer=), " +
                "Car(licensePlate=MH14BA3743, seatCount=4, convertible=0, rating=5, engineType=, manufacturer=TATA), " +
                "Car(licensePlate=MH14BK3743, seatCount=4, convertible=-1, rating=5, engineType=Gas, manufacturer=TATA), " +
                "Car(licensePlate=MH14BS3743, seatCount=4, convertible=2, rating=5, engineType=Gas, manufacturer=TATA)]\n" +
                "Valid Cars: \n" +
                "[]";

        String actual = carServiceImplementation.addCars(carList);
        assertEquals(expectedResult, actual);
    }

    @Test
    void shouldReturnAllCars() throws Exception {
        List<Car> carList = new ArrayList<>();
        carList.add(new Car("MH12BF3743", 4,0,5,"Electric","TATA"));
        when(carsRepository.findAll()).thenReturn(carList);
        String actual = carServiceImplementation.getAllCars();
        assertEquals("All Cars: \n"+carList, actual);
    }

    @Test
    void shouldNotReturnCarsForEmptyList()  {
        List<Car> carList = new ArrayList<>();
        when(carsRepository.findAll()).thenReturn(carList);
        String actual = carServiceImplementation.getAllCars();
        assertEquals("Data Not Found!", actual);
    }

    @Test
    void shouldGetCarById() throws Exception {
        Car car = new Car("MH12BF3743", 4,0,5,"Electric","TATA");
        ResponseEntity<Car> carEntity = new ResponseEntity<>(car, HttpStatus.OK);
        when(carsRepository.findById("MH12BF3743")).thenReturn(Optional.of(car));
        ResponseEntity<?> actual = carServiceImplementation.getCarById("MH12BF3743");
        assertThat(carEntity).isEqualTo(actual);
    }

    @Test
    void shouldNotGetCarByIdForIncorrectID() throws Exception {
        Car car = null;
        ResponseEntity<Car> carEntity = new ResponseEntity<>(car, HttpStatus.NOT_FOUND);
        ResponseEntity<?> actual = carServiceImplementation.getCarById("MH12BF3743");
        assertThat(carEntity).isEqualTo(actual);
    }

    @Test
    void shouldDeleteCar(){
        Car car = new Car("MH12BF3743", 4,0,5,"Electric","TATA");
        when(carsRepository.findById("MH12BF3743")).thenReturn(Optional.of(car));
        String actual = carServiceImplementation.delete("MH12BF3743");
        assertEquals("Deleted " + "MH12BF3743", actual);
    }

    @Test
    void shouldNotDeleteCarIfCarNotFound(){
        Car car = new Car("MH13BF3743", 4,0,5,"Electric","TATA");
        String actual = carServiceImplementation.delete("MH12BF3743");
        assertEquals("MH12BF3743"+" Not Found", actual);
    }

    @Test
    void shouldUpdateCar(){
        Car car = new Car("MH13BF3743", 4,0,5,"Electric","TATA");
        Car carBody = new Car("MH13BF3743", 5, 1, 5, "Gas", "Mahindra");
        when(carsRepository.findById("MH13BF3743")).thenReturn(Optional.of(car));
        when(carsRepository.save(car)).thenReturn(car);
        String actual = carServiceImplementation.update("MH13BF3743", carBody);
        assertEquals("Updated " + car, actual);
    }

    @Test
    void shouldNotUpdateCarIfCarNotFound() throws Exception {
        Car car = null;
        Car carBody = new Car("MH13BF3743", 5, 1, 5, "Gas", "Mahindra");
        when(carsRepository.findById("MH13BF3743")).thenReturn(Optional.empty());
        String actual = carServiceImplementation.update("MH13BF3743", carBody);
        assertEquals("License Plate Not Found!", actual);
    }

    @Test
    void shouldReturnCarsBasedOnFilters(){
        List<Car> carList = new ArrayList<>();
        carList.add(new Car("MH13BF3743", 5, 1, 5, "Gas", "Mahindra"));

        when(carsRepository.findAll(any(Specification.class))).thenReturn(carList);
        List<Car> actual = carServiceImplementation.searchCars("manufacturer eq Mahindra");
        assertEquals(carList, actual);
    }
}


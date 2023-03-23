package com.springboot.assignment.taxiapplication.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import javax.persistence.*;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Entity
@Table(name="selected_cars_table")
public class SelectedCars {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    @Column(name="driver_id")
    private int driverId;

    @Column(name="car_id")
    private String carId;

    public SelectedCars(int driverId, String carId) {
        this.driverId = driverId;
        this.carId = carId;
    }
}

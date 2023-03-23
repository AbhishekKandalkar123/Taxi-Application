package com.springboot.assignment.taxiapplication.model;

import lombok.*;
import javax.persistence.*;

@Entity
@Table(name="cars_table")
@NoArgsConstructor
@Getter
@Setter
@ToString
@AllArgsConstructor
public class Car {
    @Id
    private String licensePlate;
    @Column
    private int seatCount;
    @Column
    private int convertible;
    @Column
    private int rating;
    @Column
    private String engineType;
    @Column
    private String manufacturer;

}

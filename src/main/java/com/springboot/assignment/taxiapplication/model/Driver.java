package com.springboot.assignment.taxiapplication.model;

import lombok.*;
import javax.persistence.*;
@Entity
@Table(name="driver_table")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class Driver {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int driverId;

    @Column
    private String name;

    @Column
    private int isFourWheelerLicensed;

    @Column
    private int experience;

}

package org.backend.classes.vehicle;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Vehicle {
    private Integer id;
    private String reference;
    private String brand;
    private String model;
    private String color;
    private String description;
    private double price;
    private int year;
    private String fuel_type;
}

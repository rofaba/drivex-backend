package com.DriveX.DriveX.model.vehicle;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;   // ✅ IMPORTANTE
import java.util.List;        // ✅ IMPORTANTE

@Data
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "vehicles")
public class Vehicle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String reference;

    private String brand;

    private String model;

    private Integer hp;

    private Integer autonomy;
    @Column(name = "average_consumption")
    private Double averageconsumption;

    private String description;

    private BigDecimal price;

    private Integer year;

    @Column(name = "fuel_type")
    private String fuelType;

    private Long mileage;

    private String extras;
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;

    private int doors;
    @Column(name = "vehicle_type")
    private String vehicleType;


    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("vehicle")
    private List<VehicleImage> images = new ArrayList<>();
}
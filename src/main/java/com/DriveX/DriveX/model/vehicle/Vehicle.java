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

    private Double average_consumption;

    private String description;

    private BigDecimal price;

    private Integer year;

    private String fuel_type;

    private Long mileage;

    private String extras;

    private LocalDateTime created_at;

    private LocalDateTime updated_at;

    private int doors;

    private String vehicle_type;


    @OneToMany(mappedBy = "vehicle", cascade = CascadeType.ALL, orphanRemoval = true)
    @JsonIgnoreProperties("vehicle")
    private List<VehicleImage> images = new ArrayList<>();
}
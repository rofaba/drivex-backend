package com.DriveX.DriveX.model.vehicle;


import com.DriveX.DriveX.model.user.User;
import jakarta.persistence.*;

@Entity
@Table(name = "favorites",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "vehicle_id"}))
public class Favorite {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "vehicle_id", nullable = false)
    private Vehicle vehicle;

    public Favorite() {}

    public Favorite(User user, Vehicle vehicle) {
        this.user = user;
        this.vehicle = vehicle;
    }

    public Long getId() { return id; }
    public User getUser() { return user; }
    public Vehicle getVehicle() { return vehicle; }

    public void setUser(User user) { this.user = user; }
    public void setVehicle(Vehicle vehicle) { this.vehicle = vehicle; }
}
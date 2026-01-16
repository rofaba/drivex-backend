package com.DriveX.DriveX.repository;

import com.DriveX.DriveX.model.rental.Rental;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface RentalRepository extends JpaRepository<Rental, Long> {

    List<Rental> findByUserId(Long userId);

    List<Rental> findByVehicleId(Long vehicleId);

    List<Rental> findByStartDateBetween(LocalDate start, LocalDate end);

    // Optionally: find active rentals overlapping a period to check availability
    List<Rental> findByVehicleIdAndEndDateAfterAndStartDateBefore(Long vehicleId, LocalDate from, LocalDate to);
}

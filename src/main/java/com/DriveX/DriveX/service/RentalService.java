package com.DriveX.DriveX.service;

import com.DriveX.DriveX.model.rental.Rental;
import com.DriveX.DriveX.model.rental.RentalStatus;
import com.DriveX.DriveX.repository.RentalRepository;
import com.DriveX.DriveX.service.exception.RentalNotFoundException;
import com.DriveX.DriveX.service.exception.RentalOverlapException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Service
@Transactional
public class RentalService {

    private final RentalRepository repository;

    public RentalService(RentalRepository repository) {
        this.repository = repository;
    }

    /**
     * Create and persist a rental entity after checking availability.
     * Caller provides a fully populated Rental (no DTOs).
     */
    public Rental createRental(Rental rental) {
        if (!isAvailable(rental.getVehicleId(), rental.getStartDate(), rental.getEndDate())) {
            throw new RentalOverlapException("Vehicle not available for the requested period");
        }
        rental.setStatus(RentalStatus.RESERVED);
        return repository.save(rental);
    }

    @Transactional(readOnly = true)
    public Rental getRental(Long id) {
        return repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
    }

    @Transactional(readOnly = true)
    public List<Rental> findByUser(Long userId) {
        return repository.findByUserId(userId);
    }

    @Transactional(readOnly = true)
    public List<Rental> findByVehicle(Long vehicleId) {
        return repository.findByVehicleId(vehicleId);
    }

    public void cancelRental(Long id) {
        Rental r = repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        r.setStatus(RentalStatus.CANCELLED);
        repository.save(r);
    }

    public void completeRental(Long id) {
        Rental r = repository.findById(id).orElseThrow(() -> new RentalNotFoundException(id));
        r.setStatus(RentalStatus.COMPLETED);
        repository.save(r);
    }

    @Transactional(readOnly = true)
    public boolean isAvailable(Long vehicleId, LocalDate start, LocalDate end) {
        return repository.findByVehicleIdAndEndDateAfterAndStartDateBefore(vehicleId, start, end).isEmpty();
    }
}

package com.DriveX.DriveX.controller;

import com.DriveX.DriveX.dto.VehicleImageRequest;
import com.DriveX.DriveX.model.vehicle.VehicleImage;
import com.DriveX.DriveX.service.VehicleImageService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/vehicles")
public class VehicleImageController {

    private final VehicleImageService service;

    public VehicleImageController(VehicleImageService service) {
        this.service = service;
    }

    @PostMapping("/{id}/images")
    public VehicleImage uploadImage(
            @PathVariable Long id,
            @RequestBody VehicleImageRequest request) {

        return service.addImage(id, request.getImageUrl(), request.isMain());
    }

    @GetMapping("/{id}/images")
    public List<VehicleImage> listImages(@PathVariable Long id) {
        return service.getImages(id);
    }
}

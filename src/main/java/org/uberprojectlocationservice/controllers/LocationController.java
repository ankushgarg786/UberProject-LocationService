package org.uberprojectlocationservice.controllers;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.uberprojectlocationservice.dtos.DriverLocationDTO;
import org.uberprojectlocationservice.dtos.NearByDriversRequestDTO;
import org.uberprojectlocationservice.dtos.SaveDriverLocationRequestDTO;
import org.uberprojectlocationservice.services.LocationService;

import java.util.List;

@RestController
@RequestMapping("/api/v1/location")
public class LocationController {

    private final LocationService locationService;

    public LocationController(LocationService locationService) {
        this.locationService = locationService;
    }

    @PostMapping("/drivers")
    public ResponseEntity<Boolean> saveDriverLocation(@RequestBody SaveDriverLocationRequestDTO saveDriverLocationRequestDTO) {

        try {
            Boolean response = locationService.saveDriverLocation(saveDriverLocationRequestDTO.getDriverId(), saveDriverLocationRequestDTO.getLatitude(), saveDriverLocationRequestDTO.getLongitude());
            return ResponseEntity.status(HttpStatus.CREATED).body(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(false);
        }

    }

    @GetMapping("/nearby/drivers")
    public ResponseEntity<List<DriverLocationDTO>> getNearbyDrivers(@RequestBody NearByDriversRequestDTO nearByDriversRequestDTO) {
        try {
            List<DriverLocationDTO> drivers = locationService.getNearbyDrivers(nearByDriversRequestDTO.getLatitude(), nearByDriversRequestDTO.getLongitude());
            return ResponseEntity.status(HttpStatus.OK).body(drivers);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

}

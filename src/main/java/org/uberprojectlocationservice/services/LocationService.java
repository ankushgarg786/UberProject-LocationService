package org.uberprojectlocationservice.services;

import org.uberprojectlocationservice.dtos.DriverLocationDTO;

import java.util.List;

public interface LocationService {

    Boolean saveDriverLocation(String driverId,Double latitude,Double longitude);

    List<DriverLocationDTO>getNearbyDrivers(Double latitude,Double longitude);
}

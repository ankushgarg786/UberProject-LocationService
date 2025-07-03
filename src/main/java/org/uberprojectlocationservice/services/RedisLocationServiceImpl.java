package org.uberprojectlocationservice.services;

import org.springframework.data.geo.*;
import org.springframework.data.redis.connection.RedisGeoCommands;
import org.springframework.data.redis.core.GeoOperations;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.uberprojectlocationservice.dtos.DriverLocationDTO;

import java.util.ArrayList;
import java.util.List;
@Service
public class RedisLocationServiceImpl implements LocationService {

    private final StringRedisTemplate stringRedisTemplate;

    private static final String DRIVER_GEO_OPS_KEY = "drivers";

    private static final Double SEARCH_RADIUS = 5.0;

    public RedisLocationServiceImpl(StringRedisTemplate stringRedisTemplate) {
        this.stringRedisTemplate = stringRedisTemplate;
    }

    @Override
    public Boolean saveDriverLocation(String driverId, Double latitude, Double longitude) {
        try {
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            geoOps.add(
                    DRIVER_GEO_OPS_KEY
                    , new RedisGeoCommands.GeoLocation<>(driverId,new Point(latitude,longitude))
            );// basically ("drivers","driver-1",lat,long)
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    @Override
    public List<DriverLocationDTO> getNearbyDrivers(Double latitude, Double longitude) {
        try {
            GeoOperations<String, String> geoOps = stringRedisTemplate.opsForGeo();
            Distance distance = new Distance(SEARCH_RADIUS, Metrics.KILOMETERS);
            Circle within = new Circle(new Point(latitude, longitude), distance);

            GeoResults<RedisGeoCommands.GeoLocation<String>> results = geoOps.radius(DRIVER_GEO_OPS_KEY, within);// this will search all the drivers in the within(5km radius range)
            List<DriverLocationDTO> drivers = new ArrayList<>();
            for (GeoResult<RedisGeoCommands.GeoLocation<String>> result : results) {
                Point point=geoOps.position(DRIVER_GEO_OPS_KEY,result.getContent().getName()).get(0);
                DriverLocationDTO driverLocation=DriverLocationDTO.builder()
                        .driverId(result.getContent().getName())
                        .latitude(point.getX())
                        .longitude(point.getY())
                        .build();
                drivers.add(driverLocation);
            }
            return drivers;

        } catch (Exception e) {
            return new ArrayList();
        }
    }
}

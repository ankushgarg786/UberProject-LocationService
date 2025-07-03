package org.uberprojectlocationservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DriverLocationDTO {
    String driverId;
    double latitude;
    double longitude;
}


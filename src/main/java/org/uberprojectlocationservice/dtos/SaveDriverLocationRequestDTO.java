package org.uberprojectlocationservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveDriverLocationRequestDTO {
    String driverId;
    double latitude;
    double longitude;
}

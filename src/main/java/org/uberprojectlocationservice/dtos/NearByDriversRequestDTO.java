package org.uberprojectlocationservice.dtos;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class NearByDriversRequestDTO {
    double latitude;
    double longitude;
}

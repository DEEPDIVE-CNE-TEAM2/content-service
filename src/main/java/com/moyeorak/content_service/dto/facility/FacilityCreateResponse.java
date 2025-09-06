package com.moyeorak.content_service.dto.facility;

import com.moyeorak.content_service.entity.Facility;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class FacilityCreateResponse {
    private Long id;
    private String name;
    private String regionName;

    public static FacilityCreateResponse from(Facility facility) {
        return FacilityCreateResponse.builder()
                .id(facility.getId())
                .name(facility.getName())
                .regionName(facility.getRegion().getName())
                .build();
    }
}

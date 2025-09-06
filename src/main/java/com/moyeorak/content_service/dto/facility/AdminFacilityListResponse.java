package com.moyeorak.content_service.dto.facility;

import lombok.*;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class AdminFacilityListResponse {
    private Long id;
    private String name;
    private String address;
    private String contact;
    private Integer capacity;
}
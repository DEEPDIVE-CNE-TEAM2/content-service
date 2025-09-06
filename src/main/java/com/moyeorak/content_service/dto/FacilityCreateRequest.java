package com.moyeorak.content_service.dto;

import jakarta.validation.constraints.*;
import lombok.Getter;
import java.time.LocalTime;

@Getter
public class FacilityCreateRequest {

    @NotBlank
    private String name;

    @NotBlank
    private String location;

    @NotBlank
    private String address;

    private String contact;

    private String imageUrl;

    @NotNull
    private Integer capacity;

    private String description;

    private Integer area;

    @NotNull
    private LocalTime usageStartTime;

    @NotNull
    private LocalTime usageEndTime;

    @NotNull
    private Long regionId;
}
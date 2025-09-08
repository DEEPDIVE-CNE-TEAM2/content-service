package com.moyeorak.content_service.dto.feign;

import lombok.Builder;
import lombok.Getter;

import java.time.LocalDate;
import java.time.LocalTime;

@Getter
@Builder
public class ProgramDto {
    private Long id;
    private Long regionId;
    private String title;
    private int inPrice;
    private int outPrice;
    private LocalTime classStartTime;
    private LocalTime classEndTime;
    private String instructorName;
    private LocalDate cancelEndDate;
    private int capacity;
}
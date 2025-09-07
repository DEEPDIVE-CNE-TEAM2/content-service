package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.program.ProgramCreateRequest;
import com.moyeorak.content_service.dto.program.ProgramDetailResponse;
import com.moyeorak.content_service.dto.program.ProgramListResponse;
import com.moyeorak.content_service.dto.program.ProgramResponse;

import java.util.List;

public interface ProgramService {

    ProgramResponse createProgram(ProgramCreateRequest request, Long userId, String role, Long regionId);
    List<ProgramListResponse> getProgramsByRegionAndTitle(
            String role,
            Long regionId,
            String title
    );

    ProgramDetailResponse getProgramDetail(Long programId, String role, Long regionId);
    }

package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.program.*;

import java.util.List;

public interface ProgramService {

    ProgramResponse createProgram(ProgramCreateRequest request, Long userId, String role, Long regionId);

    List<ProgramListResponse> getProgramsByRegionAndTitle(
            String role,
            Long regionId,
            String title
    );

    ProgramDetailResponse getProgramDetail(Long programId, String role, Long regionId);

    Long updateProgram(Long programId, ProgramUpdateRequest request, String role, Long regionId);

    void deleteProgram(Long programId, String role, Long regionId);

}
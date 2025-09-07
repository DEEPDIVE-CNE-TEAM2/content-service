package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.feign.ProgramDto;
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

    List<ProgramDisplayResponse> getAllPrograms(Long userRegionId);
    List<ProgramDisplayResponse> getProgramsByRegion(Long regionId, Long userRegionId);
    ProgramDisplayResponse getProgramById(Long id, Long userRegionId);

    // 내부통신
    ProgramDto getProgramDtoById(Long id);

}
package com.moyeorak.content_service.service;

import com.moyeorak.content_service.dto.program.ProgramCreateRequest;
import com.moyeorak.content_service.dto.program.ProgramResponse;

public interface ProgramService {

    ProgramResponse createProgram(ProgramCreateRequest request, Long userId, String role, Long regionId);
}

package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.program.ProgramCreateRequest;
import com.moyeorak.content_service.dto.program.ProgramResponse;
import com.moyeorak.content_service.entity.Facility;
import com.moyeorak.content_service.entity.Program;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.FacilityRepository;
import com.moyeorak.content_service.repository.ProgramRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ProgramServiceImpl implements ProgramService {


    private final ProgramRepository programRepository;
    private final RegionRepository regionRepository;
    private final FacilityRepository facilityRepository;
    private final AdminAuthHelper adminAuthHelper;

    @Override
    @Transactional
    public ProgramResponse createProgram(ProgramCreateRequest request, Long userId, String role, Long regionId) {
        // 1. 권한 검증
        adminAuthHelper.validateAdmin(role);

        // 2. 지역 + 시설 조회
        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));
        Facility facility = facilityRepository.findById(request.getFacilityId())
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_FACILITY));

        // 3. 시설이 해당 지역에 속해 있는지 확인
        if (!facility.getRegion().getId().equals(region.getId())) {
            throw new BusinessException(ErrorCode.FACILITY_REGION_MISMATCH);
        }

        // 4. 프로그램 생성
        Program program = Program.builder()
                .title(request.getTitle())
                .region(facility.getRegion())
                .facility(facility)
                .category(request.getCategory())
                .target(request.getTarget())
                .instructorName(request.getInstructorName())
                .status("CLOSED".equalsIgnoreCase(request.getStatus()) ? Program.Status.CLOSED : Program.Status.OPEN)
                .usageStartDate(request.getUsageStartDate())
                .usageEndDate(request.getUsageEndDate())
                .classStartTime(request.getClassStartTime())
                .classEndTime(request.getClassEndTime())
                .registrationStartDate(request.getRegistrationStartDate())
                .registrationEndDate(request.getRegistrationEndDate())
                .cancelEndDate(request.getCancelEndDate())
                .inPrice(request.getInPrice())
                .outPrice(request.getOutPrice())
                .capacity(request.getCapacity())
                .contact(request.getContact())
                .imageUrl(request.getImageUrl())
                .description(request.getDescription())
                .build();

        Program saved = programRepository.save(program);

        return ProgramResponse.from(saved);
    }
}

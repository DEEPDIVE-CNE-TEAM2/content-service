package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.feign.ProgramDto;
import com.moyeorak.content_service.dto.program.*;
import com.moyeorak.content_service.entity.Facility;
import com.moyeorak.content_service.entity.Program;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.FacilityRepository;
import com.moyeorak.content_service.repository.ProgramRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Objects;

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

        if (programRepository.existsByTitleAndRegion_Id(request.getTitle(), regionId)) {
            throw new BusinessException(ErrorCode.DUPLICATE_PROGRAM_TITLE_IN_REGION);
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

    @Override
    @Transactional(readOnly = true)
    public List<ProgramListResponse> getProgramsByRegionAndTitle(
            String role,
            Long regionId,
            String title
    ) {
        // 1. 관리자 권한 검증
        adminAuthHelper.validateAdmin(role);

        // 지역 엔티티 조회
        Region targetRegion = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        // 프로그램 리스트 조회 (타이틀 필터 유무)
        List<Program> programs = (title == null || title.trim().isEmpty())
                ? programRepository.findByRegion(targetRegion)
                : programRepository.findByRegionAndTitleContainingIgnoreCase(targetRegion, title.trim());

        //  DTO 변환
        return programs.stream()
                .map(this::toAdminListDto)
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public ProgramDetailResponse getProgramDetail(Long programId, String role, Long regionId) {
        // 관리자 권한 검증
        adminAuthHelper.validateAdmin(role);

        // 프로그램 조회
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROGRAM));

        // DTO로 변환
        return toProgramDetailResponse(program);
    }

    @Override
    @Transactional
    public Long updateProgram(Long programId, ProgramUpdateRequest request, String role, Long regionId) {
        // 1. 관리자 권한 검증
        adminAuthHelper.validateAdmin(role);

        // 2. 프로그램 조회
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROGRAM));

        // 3. 지역 검증 - 이 관리자 지역 소속 프로그램만 수정 가능
        if (!program.getRegion().getId().equals(regionId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 4. 값이 있는 필드만 덮어쓰기
        if (request.getTitle() != null) program.setTitle(request.getTitle());
        if (request.getCategory() != null) program.setCategory(request.getCategory());
        if (request.getTarget() != null) program.setTarget(request.getTarget());
        if (request.getInstructorName() != null) program.setInstructorName(request.getInstructorName());
        if (request.getStatus() != null) {
            program.setStatus("CLOSED".equalsIgnoreCase(request.getStatus())
                    ? Program.Status.CLOSED : Program.Status.OPEN);
        }
        if (request.getUsageStartDate() != null) program.setUsageStartDate(request.getUsageStartDate());
        if (request.getUsageEndDate() != null) program.setUsageEndDate(request.getUsageEndDate());
        if (request.getClassStartTime() != null) program.setClassStartTime(request.getClassStartTime());
        if (request.getClassEndTime() != null) program.setClassEndTime(request.getClassEndTime());
        if (request.getRegistrationStartDate() != null) program.setRegistrationStartDate(request.getRegistrationStartDate());
        if (request.getRegistrationEndDate() != null) program.setRegistrationEndDate(request.getRegistrationEndDate());
        if (request.getCancelEndDate() != null) program.setCancelEndDate(request.getCancelEndDate());
        if (request.getInPrice() != null) program.setInPrice(request.getInPrice());
        if (request.getOutPrice() != null) program.setOutPrice(request.getOutPrice());
        if (request.getCapacity() != null) program.setCapacity(request.getCapacity());
        if (request.getContact() != null) program.setContact(request.getContact());
        if (request.getImageUrl() != null) program.setImageUrl(request.getImageUrl());
        if (request.getDescription() != null) program.setDescription(request.getDescription());

        // 5. 시설 변경 시, 지역 일치 검증
        if (request.getFacilityId() != null) {
            Facility facility = facilityRepository.findById(request.getFacilityId())
                    .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_FACILITY));

            if (!facility.getRegion().getId().equals(program.getRegion().getId())) {
                throw new BusinessException(ErrorCode.FACILITY_REGION_MISMATCH);
            }

            program.setFacility(facility);
        }

        return program.getId();
    }

    @Override
    @Transactional
    public void deleteProgram(Long programId, String role, Long regionId) {
        // 관리자 권한 검증
        adminAuthHelper.validateAdmin(role);

        // 프로그램 조회
        Program program = programRepository.findById(programId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROGRAM));

        // 지역 관리자 검증
        if (!program.getRegion().getId().equals(regionId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }

        // 삭제
        programRepository.delete(program);
    }

    // 일반 유저 조회
    public List<ProgramDisplayResponse> getAllPrograms(Long regionId) {
        return programRepository.findAll().stream()
                .map(program -> toDisplayResponse(program, regionId))
                .toList();
    }

    public List<ProgramDisplayResponse> getProgramsByRegion(Long targetRegionId, Long regionId) {
        return programRepository.findByRegion_Id(targetRegionId).stream()
                .map(program -> toDisplayResponse(program, regionId))
                .toList();
    }

    public ProgramDisplayResponse getProgramById(Long id, Long regionId) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROGRAM));

        return toDisplayResponse(program, regionId);
    }


    // 내부통신
    @Override
    public ProgramDto getProgramDtoById(Long id) {
        Program program = programRepository.findById(id)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_PROGRAM));

        return ProgramDto.builder()
                .id(program.getId())
                .regionId(program.getRegion().getId())
                .inPrice(program.getInPrice())
                .outPrice(program.getOutPrice())
                .classStartTime(program.getClassStartTime())
                .classEndTime(program.getClassEndTime())
                .instructorName(program.getInstructorName())
                .cancelEndDate(program.getCancelEndDate() == null ? null : program.getCancelEndDate().toString())
                .build();
    }


    // 시간 포매팅
    private String formatTimeRange(LocalTime start, LocalTime end) {
        return start + " ~ " + end;
    }

    // 날짜 "YYYY-MM-DD ~ YYYY-MM-DD" 포맷팅
    private String formatDateRange(LocalDate start, LocalDate end) {
        return start + " ~ " + end;
    }

    // 오늘 날짜 기준으로 수업 상태 판단: 수업 예정 / 진행중 / 수업 종료
    private String getProgressStatus(LocalDate start, LocalDate end) {
        LocalDate today = LocalDate.now();
        if (today.isBefore(start)) return "수업 예정";
        else if (!today.isAfter(end)) return "진행중";
        else return "수업 종료";
    }

    private ProgramListResponse toAdminListDto(Program program) {
        int currentEnrollment = 0; // 일단 0으로 시작. 나중에 enrollmentRepository.countByProgramId()로 바꿀거임

        return ProgramListResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .facilityName(program.getFacility().getName())
                .usagePeriod(formatDateRange(program.getUsageStartDate(), program.getUsageEndDate()))
                .capacity(program.getCapacity())
                .currentEnrollment(currentEnrollment)
                .progressStatus(getProgressStatus(program.getUsageStartDate(), program.getUsageEndDate()))
                .build();
    }

    private ProgramDetailResponse toProgramDetailResponse(Program program) {
        return ProgramDetailResponse.builder()
                .id(program.getId())
                .title(program.getTitle())

                .regionId(program.getRegion().getId())
                .regionName(program.getRegion().getName())
                .facilityId(program.getFacility().getId())
                .facilityName(program.getFacility().getName())

                .category(program.getCategory())
                .target(program.getTarget())
                .instructorName(program.getInstructorName())
                .status(program.getStatus().name())

                .usageStartDate(program.getUsageStartDate())
                .usageEndDate(program.getUsageEndDate())
                .registrationStartDate(program.getRegistrationStartDate())
                .registrationEndDate(program.getRegistrationEndDate())
                .cancelEndDate(program.getCancelEndDate())

                .classStartTime(program.getClassStartTime())
                .classEndTime(program.getClassEndTime())

                .usagePeriod(formatDateRange(program.getUsageStartDate(), program.getUsageEndDate()))
                .classTime(formatTimeRange(program.getClassStartTime(), program.getClassEndTime()))
                .registrationPeriod(formatDateRange(program.getRegistrationStartDate(), program.getRegistrationEndDate()))

                .inPrice(program.getInPrice())
                .outPrice(program.getOutPrice())
                .capacity(program.getCapacity())
                .contact(program.getContact())
                .imageUrl(program.getImageUrl())
                .description(program.getDescription())
                .build();
    }

    private ProgramDisplayResponse toDisplayResponse(Program program, Long regionId) {
        Long programRegionId = program.getRegion() != null ? program.getRegion().getId() : null;
        boolean inRegion = Objects.equals(regionId, programRegionId);

        return ProgramDisplayResponse.builder()
                .id(program.getId())
                .title(program.getTitle())
                .location(program.getFacility().getName())
                .target(program.getTarget())
                .usagePeriod(formatDateRange(program.getUsageStartDate(), program.getUsageEndDate()))
                .classTime(formatTimeRange(program.getClassStartTime(), program.getClassEndTime()))
                .registrationPeriod(formatDateRange(program.getRegistrationStartDate(), program.getRegistrationEndDate()))
                .cancelEndDate(program.getCancelEndDate().toString())
                .inPrice(program.getInPrice())
                .outPrice(program.getOutPrice())
                .appliedPrice(inRegion ? program.getInPrice() : program.getOutPrice())
                .inRegion(inRegion)
                .capacity(program.getCapacity())
                .contact(program.getContact())
                .description(program.getDescription())
                .imageUrl(program.getImageUrl())
                .regionId(program.getRegion().getId())
                .instructorName(program.getInstructorName())
                .build();
    }
}

package com.moyeorak.content_service.service;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.content_service.common.AdminAuthHelper;
import com.moyeorak.content_service.dto.facility.*;
import com.moyeorak.content_service.entity.Facility;
import com.moyeorak.content_service.entity.Region;
import com.moyeorak.content_service.repository.FacilityRepository;
import com.moyeorak.content_service.repository.RegionRepository;
import org.springframework.transaction.annotation.Transactional;
import com.moyeorak.common.exception.ErrorCode;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class FacilityServiceImpl implements FacilityService {

    private final RegionRepository regionRepository;
    private final FacilityRepository facilityRepository;
    private final AdminAuthHelper adminAuthHelper;

    @Override
    @Transactional
    public FacilityResponse createFacility(FacilityCreateRequest request, Long userId, String role, Long regionId) {

        adminAuthHelper.validateAdmin(role);

        Region region = regionRepository.findById(regionId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_REGION));

        if (facilityRepository.existsByNameAndRegionId(request.getName(), regionId)) {
            throw new BusinessException(ErrorCode.FACILITY_NAME_DUPLICATE);
        }

        Facility facility = Facility.builder()
                .name(request.getName())
                .location(request.getLocation())
                .address(request.getAddress())
                .contact(request.getContact())
                .imageUrl(request.getImageUrl())
                .capacity(request.getCapacity())
                .description(request.getDescription())
                .area(request.getArea())
                .usageStartTime(request.getUsageStartTime())
                .usageEndTime(request.getUsageEndTime())
                .region(region)
                .build();

        Facility saved = facilityRepository.save(facility);

        return FacilityResponse.builder()
                .id(saved.getId())
                .name(saved.getName())
                .location(saved.getLocation())
                .address(saved.getAddress())
                .contact(saved.getContact())
                .imageUrl(saved.getImageUrl())
                .capacity(saved.getCapacity())
                .description(saved.getDescription())
                .area(saved.getArea())
                .usageStartTime(saved.getUsageStartTime())
                .usageEndTime(saved.getUsageEndTime())
                .regionId(region.getId())
                .build();
    }


    @Override
    @Transactional(readOnly = true)
    public List<AdminFacilityListResponse> getFacilityList(Long regionId, String role) {
        adminAuthHelper.validateAdmin(role); // ADMIN인지 검증

        List<Facility> facilities = facilityRepository.findByRegionId(regionId);

        return facilities.stream()
                .map(facility -> AdminFacilityListResponse.builder()
                        .id(facility.getId())
                        .name(facility.getName())
                        .address(facility.getAddress())
                        .contact(facility.getContact())
                        .capacity(facility.getCapacity())
                        .build())
                .toList();
    }

    @Override
    @Transactional(readOnly = true)
    public AdminFacilityDetailResponse getFacilityDetail(Long facilityId, Long regionId, String role) {
        adminAuthHelper.validateAdmin(role);

        Facility facility = facilityRepository.findById(facilityId)
                .orElseThrow(() -> new BusinessException(ErrorCode.NOT_FOUND_FACILITY));

        if (!facility.getRegion().getId().equals(regionId)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_FACILITY_ACCESS);
        }

        return AdminFacilityDetailResponse.builder()
                .id(facility.getId())
                .name(facility.getName())
                .address(facility.getAddress())
                .usageStartTime(facility.getUsageStartTime().toString())
                .usageEndTime(facility.getUsageEndTime().toString())
                .contact(facility.getContact())
                .capacity(facility.getCapacity())
                .description(facility.getDescription())
                .imageUrl(facility.getImageUrl())
                .build();
    }

    // 일반사용자
    @Override
    @Transactional(readOnly = true)
    public List<FacilityDetailResponse> getFacilityListForUser(Long regionId) {
        List<Facility> facilities = facilityRepository.findByRegionId(regionId);

        return facilities.stream()
                .map(facility -> FacilityDetailResponse.builder()
                        .id(facility.getId())
                        .location(facility.getName()) // 이름을 location으로 보내는 구조
                        .address(facility.getAddress())
                        .usageTime(facility.getUsageStartTime() + " ~ " + facility.getUsageEndTime())
                        .capacity(facility.getCapacity())
                        .imageUrl(facility.getImageUrl())
                        .description(facility.getDescription())
                        .contact(facility.getContact())
                        .build())
                .toList();
    }
}

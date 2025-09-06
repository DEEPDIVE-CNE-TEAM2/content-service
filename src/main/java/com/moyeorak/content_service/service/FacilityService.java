package com.moyeorak.content_service.service;


import com.moyeorak.content_service.dto.FacilityCreateRequest;
import com.moyeorak.content_service.dto.FacilityResponse;

public interface FacilityService {

    // 시설 등록
    FacilityResponse createFacility(FacilityCreateRequest request, Long userId, String role, Long regionId);

    // 특정 지역의 시설 목록 조회
    //List<FacilitySimpleResponse> getFacilitiesByRegion(Long regionId);

    // 시설 상세 조회
    //FacilityDetailResponse getFacility(Long id);

    // 시설 수정
    //FacilityResponse updateFacility(Long id, FacilityUpdateRequest request);

    // 시설 삭제
    //void deleteFacility(Long id);
}
package com.moyeorak.content_service.service;


import com.moyeorak.content_service.dto.facility.*;

import java.util.List;

public interface FacilityService {

    // 시설 등록
    FacilityResponse createFacility(FacilityCreateRequest request, Long userId, String role, Long regionId);

    //시설 목록 조회
    AdminFacilityDetailResponse getFacilityDetail(Long facilityId, Long regionId, String role);
    List<AdminFacilityListResponse> getFacilityList(Long regionId, String role);

    List<FacilityDetailResponse> getFacilityListForUser(Long regionId);


    // 시설 수정
    //FacilityResponse updateFacility(Long id, FacilityUpdateRequest request);

    // 시설 삭제
    //void deleteFacility(Long id);
}
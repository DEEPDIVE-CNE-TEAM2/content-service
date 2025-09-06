package com.moyeorak.content_service.repository;

import com.moyeorak.content_service.entity.Facility;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface FacilityRepository extends JpaRepository<Facility, Long> {

    // 특정 지역에 속한 시설들 조회용
    List<Facility> findByRegionId(Long regionId);

    // 시설명 + 지역으로 유니크 검증 시
    boolean existsByNameAndRegionId(String name, Long regionId);
}

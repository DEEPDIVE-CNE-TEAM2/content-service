package com.moyeorak.content_service.repository;

import com.moyeorak.content_service.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;


public interface RegionRepository extends JpaRepository<Region, Long> {

    boolean existsByName(String name);
}

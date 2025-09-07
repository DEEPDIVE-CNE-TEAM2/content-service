package com.moyeorak.content_service.repository;

import com.moyeorak.content_service.entity.Program;
import com.moyeorak.content_service.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProgramRepository extends JpaRepository<Program, Long> {
    List<Program> findByRegion(Region region);

    List<Program> findByRegionAndTitleContainingIgnoreCase(Region region, String title);
}

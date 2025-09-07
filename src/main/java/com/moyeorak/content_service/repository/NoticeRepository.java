package com.moyeorak.content_service.repository;

import com.moyeorak.content_service.entity.Notice;
import com.moyeorak.content_service.entity.Region;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface NoticeRepository extends JpaRepository<Notice, Long> {
    List<Notice> findByRegion_IdOrderByCreatedAtDesc(Long regionId);
    List<Notice> findByRegion(Region region);



}

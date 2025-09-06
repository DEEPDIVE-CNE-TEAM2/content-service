package com.moyeorak.content_service.repository;

import com.moyeorak.content_service.entity.Program;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProgramRepository extends JpaRepository<Program, Long> {
}

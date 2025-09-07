package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.feign.ProgramDto;
import com.moyeorak.content_service.service.ProgramService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/internal/programs")
@RequiredArgsConstructor
public class InternalController {

    private final ProgramService programService;

    @GetMapping("/{id}")
    public ProgramDto getProgramById(@PathVariable Long id) {
        log.info("내부 프로그램 조회 요청 - programId={}", id);
        return programService.getProgramDtoById(id);
    }
}

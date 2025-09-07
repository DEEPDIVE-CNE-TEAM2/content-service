package com.moyeorak.content_service.controller;

import com.moyeorak.content_service.dto.MessageResponse;
import com.moyeorak.content_service.dto.notice.NoticeListResponse;
import com.moyeorak.content_service.dto.notice.NoticeRequest;
import com.moyeorak.content_service.dto.notice.NoticeResponse;
import com.moyeorak.content_service.service.NoticeService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping("/api/notices")
public class NoticeController {

    private final NoticeService noticeService;

    @Operation(summary = "공지사항 등록")
    @PostMapping
    public ResponseEntity<NoticeResponse> createNotice(
            @RequestHeader("X-User-Id") Long userId,
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId,
            @RequestBody NoticeRequest request
    ) {
        log.info("공지사항 등록 요청 - title: {}, regionId: {}, userId: {}, role: {}", request.getTitle(), regionId, userId, role);

        NoticeResponse response = noticeService.createNotice(request, userId, role, regionId);
        return ResponseEntity.status(HttpStatus.CREATED).body(response);
    }

    @GetMapping
    public ResponseEntity<List<NoticeListResponse>> getNoticeList(
            @RequestHeader("X-User-Role") String role,
            @RequestHeader("X-User-Region-Id") Long regionId
    ) {
        return ResponseEntity.ok(noticeService.getNoticeList(role, regionId));
    }

    @GetMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse> getNoticeDetail(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long noticeId
    ) {
        return ResponseEntity.ok(noticeService.getNoticeDetail(noticeId, role));
    }

    @PatchMapping("/{noticeId}")
    public ResponseEntity<NoticeResponse> updateNotice(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long noticeId,
            @RequestBody NoticeRequest dto
    ) {
        log.info("공지사항 수정 요청 - noticeId: {}, role: {}", noticeId, role);
        return ResponseEntity.ok(noticeService.updateNotice(noticeId, dto, role));
    }

    @DeleteMapping("/{noticeId}")
    public ResponseEntity<MessageResponse> deleteNotice(
            @RequestHeader("X-User-Role") String role,
            @PathVariable Long noticeId
    ) {
        log.info("공지사항 삭제 요청 - noticeId: {}, role: {}", noticeId, role);
        noticeService.deleteNotice(noticeId, role);
        return ResponseEntity.ok(new MessageResponse("공지사항이 삭제되었습니다."));
    }
}
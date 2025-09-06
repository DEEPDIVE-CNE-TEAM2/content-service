package com.moyeorak.content_service.common;

import com.moyeorak.common.exception.BusinessException;
import com.moyeorak.common.exception.ErrorCode;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AdminAuthHelper {

    public void validateAdmin(String role) {
        if (!"ADMIN".equalsIgnoreCase(role)) {
            throw new BusinessException(ErrorCode.UNAUTHORIZED_ACCESS);
        }
    }

}
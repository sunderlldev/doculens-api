package com.doculens.user.dto.response;

import com.doculens.user.model.type.UserRole;

import java.time.LocalDateTime;

public record UserListResponse(
        Long id,
        String firebaseUid,
        String email,
        String displayName,
        UserRole role,
        LocalDateTime createdAt) {
}

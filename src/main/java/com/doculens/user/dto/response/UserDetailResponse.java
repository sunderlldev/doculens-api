package com.doculens.user.dto.response;

import com.doculens.user.model.type.UserRole;

import java.time.LocalDateTime;

public record UserDetailResponse(
        Long id,
        String firebaseUid,
        String email,
        String displayName,
        String photoUrl,
        Boolean emailVerified,
        UserRole role,
        LocalDateTime createdAt) {
}

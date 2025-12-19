package com.doculens.user.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public record CreateUserRequest(
    @NotBlank(message = "firebaseUid is required")
    @Size(max = 128, message = "firebaseUid must not exceed 128 characters")
    String firebaseUid,

    @NotBlank(message = "Email is required")
    @Email(message = "Email must be valid")
    @Size(max = 255, message = "Email must not exceed 255 characters")
    String email,

    @Size(max = 255)
    String displayName,

    @Size(max = 2048)
    String photoUrl,

    Boolean emailVerified
) {}

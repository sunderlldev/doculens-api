package com.doculens.user.service;

import com.doculens.shared.exception.ResourceNotFoundException;
import com.doculens.user.model.type.UserRole;
import com.doculens.user.dto.request.CreateUserRequest;
import com.doculens.user.dto.request.UpdateUserRequest;
import com.doculens.user.dto.response.UserDetailResponse;
import com.doculens.user.dto.response.UserListResponse;
import com.doculens.user.model.User;
import com.doculens.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.web.server.ResponseStatusException;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
@Validated
public class UserService {
    private final UserRepository userRepository;

    public List<UserListResponse> getList() {
        return userRepository.findList();
    }

    public UserDetailResponse getDetailById(Long id) {
        return userRepository.findDetailById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
    }

    @Transactional
    public UserListResponse create(CreateUserRequest request) {
        if (userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        User user = new User();
        user.setFirebaseUid(request.firebaseUid());
        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setPhotoUrl(request.photoUrl());
        user.setEmailVerified(Boolean.TRUE.equals(request.emailVerified()));
        user.setRole(UserRole.USER);

        User saved = userRepository.save(user);
        return toListResponse(saved);
    }

    @Transactional
    public UserListResponse update(Long id, UpdateUserRequest request) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));

        if (!user.getEmail().equals(request.email())
                && userRepository.findByEmail(request.email()).isPresent()) {
            throw new ResponseStatusException(HttpStatus.CONFLICT, "Email already exists");
        }

        user.setEmail(request.email());
        user.setDisplayName(request.displayName());
        user.setPhotoUrl(request.photoUrl());

        User updated = userRepository.save(user);
        return toListResponse(updated);
    }

    @Transactional
    public void delete(Long id) {
        User user = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("User not found with ID: " + id));
        userRepository.delete(user);
    }

    private UserListResponse toListResponse(User user) {
        return new UserListResponse(
                user.getId(),
                user.getFirebaseUid(),
                user.getEmail(),
                user.getDisplayName(),
                user.getRole(),
                user.getCreatedAt());
    }
}

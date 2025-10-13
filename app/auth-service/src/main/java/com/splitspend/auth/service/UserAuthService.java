package com.splitspend.auth.service;

import com.splitspend.auth.controller.AuthResponseDto;
import com.splitspend.auth.controller.RegisterRequestDto;
import com.splitspend.auth.entity.User;
import org.springframework.stereotype.Service;
import com.splitspend.auth.repository.UserRepository;

import java.time.Instant;
import java.util.Date;

@Service
public class UserAuthService {

    private final UserRepository userRepository;

    public UserAuthService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public AuthResponseDto userRegistration(RegisterRequestDto requestDto) {

        User savedUser = checkIfExistingUser(requestDto);

        if (savedUser == null) {
            savedUser = createNewUser(requestDto);
        }

        return new AuthResponseDto(savedUser.getId(), savedUser.getEmail(), savedUser.getCreatedAt());
    }

    private User checkIfExistingUser(RegisterRequestDto requestDto) {
        return userRepository.findByEmail(requestDto.getEmail()).orElse(null);
    }

    private User createNewUser(RegisterRequestDto requestDto) {
        User user = new User
                (
                        requestDto.getEmail(),
                        requestDto.getPassword(),
                        Date.from(Instant.now())
                );
        return userRepository.save(user);
    }
}

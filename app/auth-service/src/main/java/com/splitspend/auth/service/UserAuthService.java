package com.splitspend.auth.service;

import com.splitspend.auth.controller.AuthResponseDto;
import com.splitspend.auth.controller.LoginRequestDto;
import com.splitspend.auth.controller.RegisterRequestDto;
import com.splitspend.auth.entity.User;
import com.splitspend.auth.repository.UserRepository;
import com.splitspend.auth.service.exception.UserAuthenticationException;
import com.splitspend.auth.service.exception.UserNotRegisteredServiceException;
import java.time.Instant;
import java.util.Date;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserAuthService {

  private final UserRepository userRepository;

  private final PasswordEncoder passwordEncoder;

  public UserAuthService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
    this.userRepository = userRepository;
    this.passwordEncoder = passwordEncoder;
  }

  public AuthResponseDto userRegistration(RegisterRequestDto requestDto) {

    User savedUser = checkIfExistingUser(requestDto);

    if (savedUser == null) {
      savedUser = createNewUser(requestDto);
    }

    return new AuthResponseDto(savedUser.getId(), savedUser.getEmail(), savedUser.getCreatedAt());
  }

  public AuthResponseDto userLogin(LoginRequestDto requestDto) {
    User user =
        userRepository
            .findByEmail(requestDto.email())
            .orElseThrow(
                () ->
                    new UserNotRegisteredServiceException(
                        "%s is not a registered email" + requestDto.email()));

    if (passwordEncoder.matches(requestDto.password(), user.getPassword())) {
      return new AuthResponseDto(user.getId(), user.getEmail(), user.getCreatedAt());
    } else {
      throw new UserAuthenticationException("Incorrect password");
    }
  }

  private User checkIfExistingUser(RegisterRequestDto requestDto) {
    return userRepository.findByEmail(requestDto.getEmail()).orElse(null);
  }

  private User createNewUser(RegisterRequestDto requestDto) {
    String encodedPassword = passwordEncoder.encode(requestDto.getPassword());
    User user = new User(requestDto.getEmail(), encodedPassword, Date.from(Instant.now()));
    return userRepository.save(user);
  }
}

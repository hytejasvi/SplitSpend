package com.splitspend.auth.controller;

import com.splitspend.auth.service.UserAuthService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

  private UserAuthService userAuthService;

  AuthController(UserAuthService userAuthService) {
    this.userAuthService = userAuthService;
  }

  @PostMapping("/register")
  public ResponseEntity<AuthResponseDto> register(
      @Valid @RequestBody RegisterRequestDto requestDto) {
    AuthResponseDto response = userAuthService.userRegistration(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }

  @PostMapping("/login")
  public ResponseEntity<AuthResponseDto> userLogin(@Valid @RequestBody LoginRequestDto requestDto) {
    AuthResponseDto response = userAuthService.userLogin(requestDto);
    return ResponseEntity.status(HttpStatus.OK).body(response);
  }
}

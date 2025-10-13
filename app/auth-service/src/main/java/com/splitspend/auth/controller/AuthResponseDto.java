package com.splitspend.auth.controller;

import java.util.Date;

public record AuthResponseDto(Long id, String email, Date createdAt) {}

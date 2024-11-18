package dev.lynxie.webapi.user.dto;

public record UserLoginResponseDto(String token, UserResponseDto user) {
}

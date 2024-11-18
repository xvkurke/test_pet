package dev.lynxie.webapi.user.security;

import dev.lynxie.webapi.user.dto.UserLoginRequestDto;
import dev.lynxie.webapi.user.dto.UserLoginResponseDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.mapper.UserMapperImpl;
import dev.lynxie.webapi.user.service.impl.UserServiceImpl;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final JwtUtil jwtUtil;
    private final AuthenticationManager authenticationManager;
    private final UserServiceImpl userService;
    private final UserMapperImpl userMapperImpl;

    public UserLoginResponseDto authenticate(UserLoginRequestDto request) {
        final Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword())
        );
        String token = jwtUtil.generateToken(authentication.getName());

        UserResponseDto userResponseDto = userMapperImpl.toUserResponseDto(
                userService.findByEmail(request.getEmail()).orElseThrow()
        );
        return new UserLoginResponseDto(token, userResponseDto);
    }

    public boolean isTokenValid(String token) {
        return jwtUtil.isValidToken(token);
    }

    public UserResponseDto getUserFromToken(String token) {
        String email = jwtUtil.getEmail(token);
        return userMapperImpl.toUserResponseDto(
                userService.findByEmail(email).orElseThrow()
        );
    }
}

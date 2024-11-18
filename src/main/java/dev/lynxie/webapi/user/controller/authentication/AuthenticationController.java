package dev.lynxie.webapi.user.controller.authentication;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import dev.lynxie.webapi.user.dto.UserLoginRequestDto;
import dev.lynxie.webapi.user.dto.UserLoginResponseDto;
import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.security.AuthenticationService;
import dev.lynxie.webapi.user.security.JwtUtil;
import dev.lynxie.webapi.user.service.UserService;
import io.jsonwebtoken.JwtException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;

@RestController
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {

    private final JwtUtil jwtUtil;
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(ControllerRoutes.AUTH_REGISTRATION)
    public ResponseEntity<Response> registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return this.response(HttpStatus.CREATED.value(),
                userService.register(requestDto));
    }

    @PostMapping(ControllerRoutes.AUTH_LOGIN)
    public ResponseEntity<Response> login(@RequestBody @Valid UserLoginRequestDto request,
                                          HttpServletResponse httpServletResponse) {
        UserLoginResponseDto userLoginResponse = authenticationService.authenticate(request);

        Cookie cookie = jwtUtil.createCookie(userLoginResponse.token());
        httpServletResponse.addCookie(cookie);

        return this.response(userLoginResponse);
    }

    @PostMapping(ControllerRoutes.AUTH_LOGOUT)
    public ResponseEntity<Response> logout(HttpServletResponse httpServletResponse) {
        jwtUtil.clearAuthTokenCookie(httpServletResponse);
        return this.response("Logged out successfully!");
    }

    @GetMapping(ControllerRoutes.AUTH_ME)
    public ResponseEntity<Response> getCurrentUser(HttpServletRequest request, HttpServletResponse response) {
        String token = Arrays.stream(request.getCookies())
                .filter(cookie -> jwtUtil.getAuthCookieName().equals(cookie.getName()))
                .findFirst()
                .map(Cookie::getValue)
                .orElse(null);

        if (token == null) {
            return this.response("Auth token not found", HttpStatus.FORBIDDEN.value());
        }

        try {
            if (!authenticationService.isTokenValid(token)) {
                jwtUtil.clearAuthTokenCookie(response);
                return this.response("Unauthorized", HttpStatus.UNAUTHORIZED.value());
            }
            UserResponseDto userInfo = authenticationService.getUserFromToken(token);
            return this.response(userInfo);
        } catch (JwtException | NullPointerException e) {
            jwtUtil.clearAuthTokenCookie(response);
            return this.response("Unauthorized", HttpStatus.UNAUTHORIZED.value());
        }
    }
}

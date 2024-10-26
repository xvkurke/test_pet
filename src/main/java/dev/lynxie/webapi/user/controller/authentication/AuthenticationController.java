package dev.lynxie.webapi.user.controller.authentication;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import dev.lynxie.webapi.user.dto.UserLoginRequestDto;
import dev.lynxie.webapi.user.dto.UserLoginResponseDto;
import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.security.AuthenticationService;
import dev.lynxie.webapi.user.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController extends BaseController {
    private final UserService userService;
    private final AuthenticationService authenticationService;

    @PostMapping(ControllerRoutes.AUTH_REGISTRATION)
    public ResponseEntity<Response> registerUser(@RequestBody @Valid UserRegistrationRequestDto requestDto) {
        return this.response(HttpStatus.CREATED.value(),
                userService.register(requestDto));
    }

    @PostMapping(ControllerRoutes.AUTH_LOGIN)
    public ResponseEntity<UserLoginResponseDto> login(@RequestBody @Valid UserLoginRequestDto request) {
        return ResponseEntity.ok(authenticationService.authenticate(request));
    }
}

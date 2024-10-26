package dev.lynxie.webapi.user.service;

import dev.lynxie.webapi.master.dto.ListResponseDto;
import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.dto.UserUpdateRequestDto;
import dev.lynxie.webapi.user.model.User;
import java.util.Optional;
import org.springframework.data.domain.Pageable;

public interface UserService {
    UserResponseDto register(UserRegistrationRequestDto requestDto);

    ListResponseDto<UserResponseDto> findAll(Pageable pageable);

    Optional<User> findById(Long id);

    void deleteById(Long id);

    UserResponseDto updateById(Long id, UserUpdateRequestDto requestDto);
}

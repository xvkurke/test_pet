package dev.lynxie.webapi.user.mapper;

import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.dto.UserUpdateRequestDto;
import dev.lynxie.webapi.user.model.User;

public interface UserMapper {
    UserResponseDto toUserResponseDto(User user);

    User toUser(UserRegistrationRequestDto registrationRequestDto);

    void updateUser(UserUpdateRequestDto userUpdateRequestDto, User user);
}

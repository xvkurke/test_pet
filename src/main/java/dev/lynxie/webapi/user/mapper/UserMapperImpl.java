package dev.lynxie.webapi.user.mapper;

import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.dto.UserUpdateRequestDto;
import dev.lynxie.webapi.user.model.User;
import org.springframework.stereotype.Component;

@Component
public class UserMapperImpl implements UserMapper {

    @Override
    public UserResponseDto toUserResponseDto(User user) {
        if (user == null) {
            return null;
        }
        UserResponseDto userResponseDto = new UserResponseDto();

        userResponseDto.setId(user.getId());
        userResponseDto.setEmail(user.getEmail());
        userResponseDto.setFirstName(user.getFirstName());
        userResponseDto.setLastName(user.getLastName());

        return userResponseDto;
    }

    @Override
    public User toUser(UserRegistrationRequestDto registrationRequestDto) {
        if (registrationRequestDto == null) {
            return null;
        }

        User user = new User();

        if (registrationRequestDto.getEmail() != null) {
            user.setEmail(registrationRequestDto.getEmail());
        }
        if (registrationRequestDto.getPassword() != null) {
            user.setPassword(registrationRequestDto.getPassword());
        }
        if (registrationRequestDto.getFirstName() != null) {
            user.setFirstName(registrationRequestDto.getFirstName());
        }
        if (registrationRequestDto.getLastName() != null) {
            user.setLastName(registrationRequestDto.getLastName());
        }

        return user;
    }

    @Override
    public void updateUser(UserUpdateRequestDto userUpdateRequestDto, User user) {
        if (userUpdateRequestDto == null || user == null) {
            return;
        }
        user.setFirstName(userUpdateRequestDto.getFirstName());
        user.setLastName(userUpdateRequestDto.getLastName());
    }
}

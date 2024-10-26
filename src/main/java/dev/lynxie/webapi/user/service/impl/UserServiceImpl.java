package dev.lynxie.webapi.user.service.impl;

import dev.lynxie.webapi.master.dto.ListResponseDto;
import dev.lynxie.webapi.user.dto.UserRegistrationRequestDto;
import dev.lynxie.webapi.user.dto.UserResponseDto;
import dev.lynxie.webapi.user.dto.UserUpdateRequestDto;
import dev.lynxie.webapi.master.exception.EntityNotFoundException;
import dev.lynxie.webapi.user.exception.RegistrationException;
import dev.lynxie.webapi.user.mapper.UserMapper;
import dev.lynxie.webapi.user.model.User;
import dev.lynxie.webapi.user.model.UserRole;
import dev.lynxie.webapi.user.repository.UserRepository;
import dev.lynxie.webapi.user.repository.UserRoleRepository;
import java.util.Optional;
import java.util.Set;

import dev.lynxie.webapi.user.service.UserService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final UserMapper userMapper;
    private final UserRoleRepository roleRepository;

    @Override
    public UserResponseDto register(UserRegistrationRequestDto requestDto) {
        if (userRepository.existsByEmail(requestDto.getEmail())) {
            throw new RegistrationException(String.format("User with this email '%s' already exists", requestDto.getEmail()));
        }

        User user = userMapper.toUser(requestDto)
                .setPassword(passwordEncoder.encode(requestDto.getPassword()))
                .setRoles(Set.of(roleRepository.findByRole(UserRole.RoleName.ROLE_USER)));

        return userMapper.toUserResponseDto(userRepository.save(user));
    }

    @Override
    public ListResponseDto<UserResponseDto> findAll(Pageable pageable) {
        Page<User> usersPage = userRepository.findAll(pageable);
        return new ListResponseDto<UserResponseDto>()
                .setList(usersPage.stream()
                        .map(userMapper::toUserResponseDto)
                        .toList())
                .setTotalRows(usersPage.getTotalElements());
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
        userRepository.deleteById(id);
    }

    @Override
    public UserResponseDto updateById(Long id, UserUpdateRequestDto requestDto) {
        User user = userRepository.findById(id).orElseThrow(
                () -> new EntityNotFoundException(String.format("User with id '%s' not found", id))
        );
        userMapper.updateUser(requestDto, user);
        userRepository.save(user);
        return userMapper.toUserResponseDto(user);
    }
}

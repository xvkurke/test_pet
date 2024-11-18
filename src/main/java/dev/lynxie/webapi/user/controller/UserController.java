package dev.lynxie.webapi.user.controller;

import static dev.lynxie.webapi.utils.PageableUtils.USERS_ORDER_LIST;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import dev.lynxie.webapi.user.dto.UserUpdateRequestDto;
import dev.lynxie.webapi.user.mapper.UserMapper;
import dev.lynxie.webapi.user.model.User;
import dev.lynxie.webapi.user.service.UserService;
import dev.lynxie.webapi.utils.PageableUtils;
import jakarta.validation.Valid;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class UserController extends BaseController {
    private final UserService userService;
    private final UserMapper userMapper;

    @GetMapping(ControllerRoutes.USERS_GET)
    public ResponseEntity<Response> getAll(@PageableDefault Pageable pageable) {
        return this.response(userService.findAll(
                PageableUtils.generatePageable(pageable.getPageNumber(),
                        pageable.getPageSize(), USERS_ORDER_LIST)));
    }

    @GetMapping(ControllerRoutes.USER_GET)
    public ResponseEntity<Response> get(@PathVariable("id") Long id) {
        Optional<User> userOptional = userService.findById(id);
        return userOptional.map(category -> this.response(userMapper.toUserResponseDto(category)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(ControllerRoutes.USER_UPDATE)
    public ResponseEntity<Response> updateById(@PathVariable("id") Long id,
                                               @RequestBody @Valid UserUpdateRequestDto user) {
        return this.response(userService.updateById(id, user));
    }
    
    @DeleteMapping(ControllerRoutes.USER_DELETE)
    public ResponseEntity<Void> deleteById(@PathVariable("id") Long id) {
        userService.deleteById(id);
        return ResponseEntity.noContent().build();
    }
}

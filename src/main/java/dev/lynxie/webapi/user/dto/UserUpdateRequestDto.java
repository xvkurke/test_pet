package dev.lynxie.webapi.user.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
public class UserUpdateRequestDto {
    @NotBlank
    private String firstName;

    @NotBlank
    private String lastName;
}

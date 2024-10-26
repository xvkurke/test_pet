package dev.lynxie.webapi.tracker.dto.category;

import dev.lynxie.webapi.tracker.dto.ResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class CategoryResponseDto extends ResponseDto {
    private Long id;
    private String name;
    private String description;
}

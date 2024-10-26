package dev.lynxie.webapi.tracker.dto.category;

import dev.lynxie.webapi.tracker.dto.RequestDto;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.experimental.Accessors;

@Data
@Accessors(chain = true)
@EqualsAndHashCode(callSuper = true)
public class UpdateCategoryRequestDto extends RequestDto {
    private String name;
    private String description;
}

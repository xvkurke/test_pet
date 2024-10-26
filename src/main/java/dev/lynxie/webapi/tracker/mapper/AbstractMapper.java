package dev.lynxie.webapi.tracker.mapper;

import dev.lynxie.webapi.tracker.dto.RequestDto;
import dev.lynxie.webapi.tracker.dto.ResponseDto;
import dev.lynxie.webapi.tracker.model.AbstractModel;

public interface AbstractMapper<
        TModel extends AbstractModel,
        TRequestDto extends RequestDto,
        TResponseDto extends ResponseDto
> {
    TResponseDto toResponseDto(TModel model);
    TModel toEntity(TRequestDto dto);
}

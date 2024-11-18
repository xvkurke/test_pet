package dev.lynxie.webapi.master.mapper;

import dev.lynxie.webapi.master.dto.RequestDto;
import dev.lynxie.webapi.master.dto.ResponseDto;
import dev.lynxie.webapi.master.model.AbstractModel;

public interface AbstractMapper<
        TModel extends AbstractModel,
        TRequestDto extends RequestDto,
        TResponseDto extends ResponseDto
> {
    TResponseDto toResponseDto(TModel model);
    TModel toEntity(TRequestDto dto);
}

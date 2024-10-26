package dev.lynxie.webapi.tracker.mapper;

import dev.lynxie.webapi.user.model.User;
import dev.lynxie.webapi.tracker.dto.category.CategoryResponseDto;
import dev.lynxie.webapi.tracker.dto.category.CreateCategoryRequestDto;
import dev.lynxie.webapi.tracker.model.Category;
import org.springframework.stereotype.Component;

@Component
public class CategoryMapper implements AbstractMapper<
        Category, CreateCategoryRequestDto, CategoryResponseDto> {

    @Override
    public CategoryResponseDto toResponseDto(Category model) {
        if (model == null) {
            return null;
        }

        return new CategoryResponseDto()
                .setId(model.getId())
                .setName(model.getName())
                .setDescription(model.getDescription());
    }

    @Override
    public Category toEntity(CreateCategoryRequestDto dto) {
        if (dto == null) {
            return null;
        }

        // TODO: Get user from DB by Id.
        User user = new User().setId(dto.getUserId());
        
        return new Category()
                .setName(dto.getName())
                .setDescription(dto.getDescription())
                .setUser(user);
    }
}

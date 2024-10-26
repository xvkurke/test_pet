package dev.lynxie.webapi.tracker.service;

import dev.lynxie.webapi.tracker.dto.category.CreateCategoryRequestDto;
import dev.lynxie.webapi.tracker.dto.category.UpdateCategoryRequestDto;
import dev.lynxie.webapi.tracker.model.Category;

import java.util.List;
import java.util.Optional;

public interface CategoryService {
    List<Category> getAll();

    Optional<Category> getById(Long id);

    Optional<Category> getByName(String name);

    Category create(CreateCategoryRequestDto dto);

    Category update(Long id, UpdateCategoryRequestDto dto);

    void delete(Long id);
}

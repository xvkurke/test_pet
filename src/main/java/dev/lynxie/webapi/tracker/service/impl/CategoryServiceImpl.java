package dev.lynxie.webapi.tracker.service.impl;

import dev.lynxie.webapi.tracker.dto.category.CreateCategoryRequestDto;
import dev.lynxie.webapi.tracker.dto.category.UpdateCategoryRequestDto;
import dev.lynxie.webapi.tracker.exception.CategoryNotFoundException;
import dev.lynxie.webapi.tracker.mapper.CategoryMapper;
import dev.lynxie.webapi.tracker.model.Category;
import dev.lynxie.webapi.tracker.repository.CategoryRepository;
import dev.lynxie.webapi.tracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class CategoryServiceImpl implements CategoryService {

    private final CategoryRepository categoryRepository;
    private final CategoryMapper categoryMapper;

    @Override
    public List<Category> getAll() {
        return categoryRepository.findAll();
    }

    @Override
    public Optional<Category> getById(Long id) {
        return categoryRepository.findById(id);
    }

    @Override
    public Optional<Category> getByName(String name) {
        return categoryRepository.findByName(name);
    }

    @Override
    public Category create(CreateCategoryRequestDto dto) {
        Category category = categoryMapper.toEntity(dto);
        return categoryRepository.save(category);
    }

    @Override
    public Category update(Long id, UpdateCategoryRequestDto dto) {
        Category existingCategory = categoryRepository.findById(id)
                .orElseThrow(() -> new CategoryNotFoundException(String
                        .format("Category by id '%d' not found", id)))
                .setName(dto.getName())
                .setDescription(dto.getDescription());
        return categoryRepository.save(existingCategory);
    }

    @Override
    public void delete(Long id) {
        if (categoryRepository.existsById(id)) {
            categoryRepository.deleteById(id);
        } else {
            throw new CategoryNotFoundException(String
                    .format("Category by id '%d' not found", id));
        }
    }
}

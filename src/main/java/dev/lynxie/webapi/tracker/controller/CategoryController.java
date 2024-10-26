package dev.lynxie.webapi.tracker.controller;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import dev.lynxie.webapi.tracker.dto.category.CategoryResponseDto;
import dev.lynxie.webapi.tracker.dto.category.CreateCategoryRequestDto;
import dev.lynxie.webapi.tracker.dto.category.UpdateCategoryRequestDto;
import dev.lynxie.webapi.tracker.mapper.CategoryMapper;
import dev.lynxie.webapi.tracker.model.Category;
import dev.lynxie.webapi.tracker.service.CategoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@Slf4j
@RequiredArgsConstructor
public class CategoryController extends BaseController {
    
    private final CategoryService categoryService;
    private final CategoryMapper categoryMapper;
    
    @GetMapping(ControllerRoutes.CATEGORIES_GET_ALL)
    public ResponseEntity<Response> getAll() {
        log.info("GET {}", ControllerRoutes.CATEGORIES_GET_ALL);
        List<Category> categories = categoryService.getAll();
        List<CategoryResponseDto> responseDtoList = categories.stream()
                .map(categoryMapper::toResponseDto)
                .toList();
        return this.response(responseDtoList);
    }
    
    @PostMapping(ControllerRoutes.CATEGORIES_CREATE)
    public ResponseEntity<Response> create(@RequestBody CreateCategoryRequestDto dto) {
        log.info("POST {}", ControllerRoutes.CATEGORIES_CREATE);
        Category createdCategory = categoryService.create(dto);
        return this.response(HttpStatus.CREATED.value(),
                categoryMapper.toResponseDto(createdCategory));
    }
    
    @GetMapping(ControllerRoutes.CATEGORIES_GET)
    public ResponseEntity<Response> get(@PathVariable("id") Long id) {
        log.info("GET {}", ControllerRoutes.CATEGORIES_GET.replace("{id}", id.toString()));
        Optional<Category> categoryOpt = categoryService.getById(id);
        return categoryOpt.map(category -> this.response(categoryMapper.toResponseDto(category)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }
    
    @PutMapping(ControllerRoutes.CATEGORIES_UPDATE)
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody UpdateCategoryRequestDto dto) {
        log.info("PUT {}", ControllerRoutes.CATEGORIES_UPDATE.replace("{id}", id.toString()));
        Category updatedCategory = categoryService.update(id, dto);
        return this.response(categoryMapper.toResponseDto(updatedCategory));
    }
    
    @DeleteMapping(ControllerRoutes.CATEGORIES_DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("DELETE {}", ControllerRoutes.CATEGORIES_DELETE.replace("{id}", id.toString()));
        categoryService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

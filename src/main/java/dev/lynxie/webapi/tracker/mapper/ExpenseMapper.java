package dev.lynxie.webapi.tracker.mapper;

import dev.lynxie.webapi.user.model.User;
import dev.lynxie.webapi.user.repository.UserRepository;
import dev.lynxie.webapi.tracker.dto.expense.CreateExpenseRequestDto;
import dev.lynxie.webapi.tracker.dto.expense.ExpenseResponseDto;
import dev.lynxie.webapi.tracker.model.Category;
import dev.lynxie.webapi.tracker.model.Expense;
import dev.lynxie.webapi.tracker.repository.CategoryRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.util.Assert;

@Component
@RequiredArgsConstructor
public class ExpenseMapper implements AbstractMapper<
        Expense, CreateExpenseRequestDto, ExpenseResponseDto> {

    private final CategoryRepository categoryRepository;
    private final UserRepository userRepository;

    @Override
    public ExpenseResponseDto toResponseDto(Expense model) {
        if (model == null) {
            return null;
        }

        return new ExpenseResponseDto()
                .setId(model.getId())
                .setAmount(model.getAmount())
                .setCurrency(model.getCurrency())
                .setCategoryId(model.getCategory().getId())
                .setCategoryName(model.getCategory().getName())
                .setDescription(model.getDescription())
                .setCreateDate(model.getCreateDate());
    }

    @Override
    public Expense toEntity(CreateExpenseRequestDto dto) {
        if (dto == null) {
            return null;
        }

        Category category = categoryRepository.findById(dto.getCategoryId())
                .orElse(null);

        Assert.notNull(category, String.format("Category by id '%d' not found", dto.getCategoryId()));
        User user = userRepository.findById(category.getUser().getId())
                .orElse(null);
        
        return new Expense()
                .setAmount(dto.getAmount())
                .setCurrency(dto.getCurrency())
                .setDescription(dto.getDescription())
                .setCreateDate(dto.getCreateDate())
                .setCategory(category)
                .setUser(user);
    }
}

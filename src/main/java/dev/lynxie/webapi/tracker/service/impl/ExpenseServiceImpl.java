package dev.lynxie.webapi.tracker.service.impl;

import dev.lynxie.webapi.tracker.dto.expense.CreateExpenseRequestDto;
import dev.lynxie.webapi.tracker.dto.expense.UpdateExpenseRequestDto;
import dev.lynxie.webapi.tracker.exception.ExpenseNotFoundException;
import dev.lynxie.webapi.tracker.mapper.ExpenseMapper;
import dev.lynxie.webapi.tracker.model.Expense;
import dev.lynxie.webapi.tracker.repository.CategoryRepository;
import dev.lynxie.webapi.tracker.repository.ExpenseRepository;
import dev.lynxie.webapi.tracker.service.ExpenseService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class ExpenseServiceImpl implements ExpenseService {

    private final ExpenseRepository expenseRepository;
    private final ExpenseMapper expenseMapper;
    private final CategoryRepository categoryRepository;

    @Override
    public List<Expense> getAll() {
        return expenseRepository.findAll();
    }

    @Override
    public Optional<Expense> getById(Long id) {
        return expenseRepository.findById(id);
    }

    @Override
    public Expense create(CreateExpenseRequestDto dto) {
        Expense expense = expenseMapper.toEntity(dto);
        return expenseRepository.save(expense);
    }

    @Override
    public Expense update(Long id, UpdateExpenseRequestDto dto) {
        Expense existingExpense = expenseRepository.findById(id)
                .orElseThrow(() -> new ExpenseNotFoundException(String
                        .format("Expense by id '%d' not found", id)))
                .setAmount(dto.getAmount())
                .setCurrency(dto.getCurrency())
                .setDescription(dto.getDescription())
                .setCategory(categoryRepository.findById(dto.getCategoryId())
                        .orElse(null));
        return expenseRepository.save(existingExpense);
    }

    @Override
    public void delete(Long id) {
        if (expenseRepository.existsById(id)) {
            expenseRepository.deleteById(id);
        } else {
            throw new ExpenseNotFoundException(String
                    .format("Expense by id '%d' not found", id));
        }
    }
}

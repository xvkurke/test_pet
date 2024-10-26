package dev.lynxie.webapi.tracker.service;

import dev.lynxie.webapi.tracker.dto.expense.CreateExpenseRequestDto;
import dev.lynxie.webapi.tracker.dto.expense.UpdateExpenseRequestDto;
import dev.lynxie.webapi.tracker.model.Expense;

import java.util.List;
import java.util.Optional;

public interface ExpenseService {
    List<Expense> getAll();

    Optional<Expense> getById(Long id);

    Expense create(CreateExpenseRequestDto dto);

    Expense update(Long id, UpdateExpenseRequestDto dto);

    void delete(Long id);
}

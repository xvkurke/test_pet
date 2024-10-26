package dev.lynxie.webapi.tracker.controller;

import dev.lynxie.webapi.config.ControllerRoutes;
import dev.lynxie.webapi.master.controller.BaseController;
import dev.lynxie.webapi.master.dto.Response;
import dev.lynxie.webapi.tracker.dto.expense.CreateExpenseRequestDto;
import dev.lynxie.webapi.tracker.dto.expense.ExpenseResponseDto;
import dev.lynxie.webapi.tracker.dto.expense.UpdateExpenseRequestDto;
import dev.lynxie.webapi.tracker.mapper.ExpenseMapper;
import dev.lynxie.webapi.tracker.model.Expense;
import dev.lynxie.webapi.tracker.service.ExpenseService;
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
public class ExpenseController extends BaseController {

    private final ExpenseService expenseService;
    private final ExpenseMapper expenseMapper;

    @GetMapping(ControllerRoutes.EXPENSES_GET_ALL)
    public ResponseEntity<Response> getAll() {
        log.info("GET {}", ControllerRoutes.EXPENSES_GET_ALL);
        List<Expense> expenses = expenseService.getAll();
        List<ExpenseResponseDto> responseDtoList = expenses.stream()
                .map(expenseMapper::toResponseDto)
                .toList();
        return this.response(responseDtoList);
    }

    @PostMapping(ControllerRoutes.EXPENSES_CREATE)
    public ResponseEntity<Response> create(@RequestBody CreateExpenseRequestDto dto) {
        log.info("POST {}", ControllerRoutes.EXPENSES_CREATE);
        Expense createdExpense = expenseService.create(dto);
        return this.response(HttpStatus.CREATED.value(), expenseMapper.toResponseDto(createdExpense));
    }

    @GetMapping(ControllerRoutes.EXPENSES_GET)
    public ResponseEntity<Response> get(@PathVariable("id") Long id) {
        log.info("GET {}", ControllerRoutes.EXPENSES_GET.replace("{id}", id.toString()));
        Optional<Expense> expenseOpt = expenseService.getById(id);
        return expenseOpt.map(expense -> this.response(expenseMapper.toResponseDto(expense)))
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PutMapping(ControllerRoutes.EXPENSES_UPDATE)
    public ResponseEntity<Response> update(@PathVariable Long id, @RequestBody UpdateExpenseRequestDto dto) {
        log.info("PUT {}", ControllerRoutes.EXPENSES_UPDATE.replace("{id}", id.toString()));
        Expense updatedExpense = expenseService.update(id, dto);
        return this.response(expenseMapper.toResponseDto(updatedExpense));
    }

    @DeleteMapping(ControllerRoutes.EXPENSES_DELETE)
    public ResponseEntity<?> delete(@PathVariable("id") Long id) {
        log.info("DELETE {}", ControllerRoutes.EXPENSES_DELETE.replace("{id}", id.toString()));
        expenseService.delete(id);
        return ResponseEntity.noContent().build();
    }
}

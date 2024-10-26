package dev.lynxie.webapi.tracker.repository;

import dev.lynxie.webapi.tracker.model.Expense;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ExpenseRepository extends JpaRepository<Expense, Long> { }

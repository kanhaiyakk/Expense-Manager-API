package com.cts.expensemanagerapi.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.cts.expensemanagerapi.entity.Expense;
import com.cts.expensemanagerapi.service.ExpenseService;

import jakarta.validation.Valid;

@RestController
@RequestMapping("/expense")
public class ExpenseController {
    @Autowired
    private ExpenseService expenseService;

    @GetMapping("/expenses")
    public ResponseEntity< List<Expense>> getAllExpense(Pageable page) {
        return new ResponseEntity<>(expenseService.getAllExpenses(page).toList(),HttpStatus.OK);
    }

    @GetMapping("/expenses/{id}")
    public ResponseEntity<Expense> getExpenseById(@PathVariable Long id) {
        return new ResponseEntity<>( expenseService.getExpenseById(id),HttpStatus.OK);
    }

    @ResponseStatus(value = HttpStatus.NO_CONTENT)
    @DeleteMapping("/expenses")
    public void deleteExpenseById(@RequestParam Long id) {
        expenseService.deleteExpenseById(id);
    }

    //@ResponseStatus(value = HttpStatus.CREATED)
    @PostMapping("/expenses")
    public ResponseEntity<Expense> saveExpenseDetails(@Valid @RequestBody Expense expense) {
        return new ResponseEntity<>(expenseService.saveExpenseDetails(expense),HttpStatus.CREATED);
    }

    @PutMapping("/expenses/{id}")
    public Expense updateExpenseDetails(@PathVariable Long id, @RequestBody Expense expense) {
        return expenseService.updateExpenseDetails(id, expense);
    }
}


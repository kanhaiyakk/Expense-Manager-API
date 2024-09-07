package com.cts.expensemanagerapi.serviceImpl;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import com.cts.expensemanagerapi.entity.Expense;
import com.cts.expensemanagerapi.exceptions.ResourceNotFoundException;
import com.cts.expensemanagerapi.repository.ExpenseRepository;
import com.cts.expensemanagerapi.service.ExpenseService;

@Service
public class ExpenseServiceImpl implements ExpenseService {
    @Autowired
    private ExpenseRepository expenseRepository;
    //To get all items
    @Override
    public Page<Expense> getAllExpenses(Pageable page) {
        return expenseRepository.findAll(page);
    }

    //Get item by providing id
    @Override
    public Expense getExpenseById(Long id) {
        Optional<Expense> expense=expenseRepository.findById(id);
        if(expense.isPresent()){
            return expense.get();
        }
        throw new ResourceNotFoundException("Expense is not found for the id "+ id);
    }


    // Delete item by giving id
    @Override
    public void deleteExpenseById(Long id) {
        Expense expense = getExpenseById(id);
        expenseRepository.delete(expense);
    }

    // Save item in database
    @Override
    public Expense saveExpenseDetails(Expense expense) {
        return expenseRepository.save(expense);
    }


    // Update item by of any entity
    @Override
    public Expense updateExpenseDetails(Long id, Expense expense) {
        Expense existingExpense= getExpenseById(id);
        existingExpense.setName(expense.getName() != null ? expense.getName() : existingExpense.getName());
        existingExpense.setDescription(expense.getDescription() != null ? expense.getDescription() : existingExpense.getDescription());
        existingExpense.setCategory(expense.getCategory() != null ? expense.getCategory() : existingExpense.getCategory());
        existingExpense.setDate(expense.getDate() != null ? expense.getDate() : existingExpense.getDate());
        existingExpense.setAmount(expense.getAmount() != null ? expense.getAmount() : existingExpense.getAmount());
        return expenseRepository.save(existingExpense);
    }


}

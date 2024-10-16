package com.cts.expensemanagerapi.expenseManagerTest;

import com.cts.expensemanagerapi.entity.Expense;
import com.cts.expensemanagerapi.repository.ExpenseRepository;
import com.cts.expensemanagerapi.serviceImpl.ExpenseServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import java.util.Optional;
import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Timestamp;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest
public class ExpenseServiceTest {

    @Mock
    private ExpenseRepository expenseRepository;

    @InjectMocks
    private ExpenseServiceImpl expenseService;


    private Expense expense;

    @BeforeEach
    public void setup(){
        expense = Expense.builder()
                .id(1L)
                .name("Internate Bill")
                .description("internate bill")
                .amount(BigDecimal.valueOf(2233.00))
                .category("Bills")
                .date(new Date(System.currentTimeMillis()))
                .createdAt(new Timestamp(System.currentTimeMillis()))
                .updatedAt(new Timestamp(System.currentTimeMillis()))
                .build();
    }

    @Test
    public void testCreateExpense(){

        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(expense);

        Expense createdExpense = expenseService.saveExpenseDetails(expense);
        assertNotNull(createdExpense);
        assertEquals("Internate Bill",createdExpense.getName());
        assertEquals(BigDecimal.valueOf(2233.00),createdExpense.getAmount());
        assertEquals("Bills",createdExpense.getCategory());

        // Optionally, verify that the save method was called
        Mockito.verify(expenseRepository, Mockito.times(1)).save(Mockito.any(Expense.class));
    }

    @Test
    public void testGetExpenseById() {
        // Arrange: Mock the repository's findById method to return Optional.of(expense)
        Mockito.when(expenseRepository.findById(Mockito.anyLong()))
                .thenReturn(Optional.of(expense));

        // Act: Call the actual service method
        Expense foundedExpense = expenseService.getExpenseById(1L);

        // Assert: Check that the retrieved expense matches the expected values
        assertNotNull(foundedExpense);
        assertEquals("Internate Bill", foundedExpense.getName());
        assertEquals(BigDecimal.valueOf(2233.00), foundedExpense.getAmount());
        assertEquals("Bills", foundedExpense.getCategory());
    }

    @Test
    public void testUpdateExpense(){
        Mockito.when(expenseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expense));
        Mockito.when(expenseRepository.save(Mockito.any(Expense.class))).thenReturn(expense);

// Modifying the item's details
        expense.setName("Internate Bill");
        expense.setAmount(BigDecimal.valueOf(2233.00));
        expense.setCategory("Bills");


        Expense updatedExpense = expenseService.updateExpenseDetails(1L,expense);

        assertNotNull(updatedExpense);
        assertEquals("Internate Bill",updatedExpense.getName());
        assertEquals(BigDecimal.valueOf(2233.00),updatedExpense.getAmount());
        assertEquals("Bills",updatedExpense.getCategory());

        Mockito.verify(expenseRepository, Mockito.times(1)).findById(1L);
        Mockito.verify(expenseRepository, Mockito.times(1)).save(expense);


    }

    @Test
    public void testDeleteExpense(){
        // Mocking the repository's findById method to return the item
        Mockito.when(expenseRepository.findById(Mockito.anyLong())).thenReturn(Optional.of(expense));

        // Mocking the repository's deleteById method to do nothing
        Mockito.doNothing().when(expenseRepository).deleteById(Mockito.anyLong());


        // Calling the service method to delete the item
        expenseService.deleteExpenseById(1L);

        // Verifying that the repository's deleteById method was called once
        Mockito.verify(expenseRepository, Mockito.times(1)).findById(1L);
    }
}

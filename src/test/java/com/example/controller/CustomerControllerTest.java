package com.example.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.Customer;
import com.example.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerControllerTest {

    @InjectMocks
    private CustomerController customerController;

    @Mock
    private CustomerService customerService;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFullName("John Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setBalance(100.0);
    }

    @Test
    void testAddCustomer() {
        customerController.addCustomer(testCustomer);
        verify(customerService, times(1)).addCustomer(testCustomer);
    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;
        customerController.deleteCustomer(customerId);
        verify(customerService, times(1)).deleteCustomer(customerId);
    }

    @Test
    void testListCustomers() {
        Iterable<Customer> customers = customerController.listCustomers();
        verify(customerService, times(1)).listCustomers();
    }

    @Test
    void testDeposit() {
        Long customerId = 1L;
        double amount = 50.0;
        when(customerService.deposit(customerId, amount)).thenReturn(150.0);
        double result = customerController.deposit(customerId, amount);
        assertEquals(150.0, result, 0.0);
    }

    @Test
    void testWithdraw() {
        Long customerId = 1L;
        double amount = 50.0;
        when(customerService.withdraw(customerId, amount)).thenReturn(50.0);
        double result = customerController.withdraw(customerId, amount);
        assertEquals(50.0, result, 0.0);
    }

    @Test
    void testUpdateProfile() {
        Long customerId = 1L;
        String fullName = "Jane Doe";
        String email = "jane.doe@example.com";
        customerController.updateProfile(customerId, fullName, email);
        verify(customerService, times(1)).updateProfile(customerId, fullName, email);
    }

    @Test
    void testCheckBalance() {
        Long customerId = 1L;
        double balance = 100.0;
        when(customerService.checkBalance(customerId)).thenReturn(balance);
        double result = customerController.checkBalance(customerId);
        assertEquals(balance, result, 0.0);
    }

    @Test
    void testChangePassword() {
        Long customerId = 1L;
        String newPassword = "newPassword";
        customerController.changePassword(customerId, newPassword);
        verify(customerService, times(1)).changePassword(customerId, newPassword);
    }
}

package com.example.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.example.model.Customer;
import com.example.repository.CustomerRepository;
import com.example.service.CustomerService;

@ExtendWith(MockitoExtension.class)
public class CustomerServiceTest {

    @InjectMocks
    private CustomerService customerService;

    @Mock
    private CustomerRepository customerRepository;

    private Customer testCustomer;

    @BeforeEach
    void setUp() {
        testCustomer = new Customer();
        testCustomer.setId(1L);
        testCustomer.setFullName("John Doe");
        testCustomer.setEmail("john.doe@example.com");
        testCustomer.setBalance(100.0);
    }

//    @Test
//    void testAddCustomer() {
//        customerService.addCustomer(testCustomer);
//        verify(customerRepository, times(1)).save(testCustomer);
//    }

    @Test
    void testDeleteCustomer() {
        Long customerId = 1L;
        customerService.deleteCustomer(customerId);
        verify(customerRepository, times(1)).deleteById(customerId);
    }

//    @Test
//    void testListCustomers() {
//        // Mock data
//        Iterable<Customer> customers = mock(Iterable.class);
//        when(customerRepository.findAll()).thenReturn((List<Customer>) customers);
//        
//        Iterable<Customer> result = customerService.listCustomers();
//        assertEquals(customers, result);
//    }

    @Test
    void testDeposit() {
        Long customerId = 1L;
        double amount = 50.0;
        Customer customer = new Customer();
        customer.setBalance(100.0);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        double result = customerService.deposit(customerId, amount);
        assertEquals(150.0, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testWithdrawWithSufficientBalance() {
        Long customerId = 1L;
        double amount = 50.0;
        Customer customer = new Customer();
        customer.setBalance(100.0);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        double result = customerService.withdraw(customerId, amount);
        assertEquals(50.0, result);
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testWithdrawWithInsufficientBalance() {
        Long customerId = 1L;
        double amount = 150.0;
        Customer customer = new Customer();
        customer.setBalance(100.0);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        assertThrows(RuntimeException.class, () -> customerService.withdraw(customerId, amount));
    }

    @Test
    void testUpdateProfile() {
        Long customerId = 1L;
        String fullName = "Jane Doe";
        String email = "jane.doe@example.com";
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        customerService.updateProfile(customerId, fullName, email);
        
        assertEquals(fullName, customer.getFullName());
        assertEquals(email, customer.getEmail());
        verify(customerRepository, times(1)).save(customer);
    }

    @Test
    void testCheckBalance() {
        Long customerId = 1L;
        double balance = 100.0;
        Customer customer = new Customer();
        customer.setBalance(balance);
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        double result = customerService.checkBalance(customerId);
        assertEquals(balance, result);
    }

    @Test
    void testChangePassword() {
        Long customerId = 1L;
        String newPassword = "newPassword";
        Customer customer = new Customer();
        when(customerRepository.findById(customerId)).thenReturn(Optional.of(customer));
        
        customerService.changePassword(customerId, newPassword);
        
        assertEquals(newPassword, customer.getPassword());
        verify(customerRepository, times(1)).save(customer);
    }
}

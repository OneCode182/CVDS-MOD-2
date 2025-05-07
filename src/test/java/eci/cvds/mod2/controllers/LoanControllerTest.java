package eci.cvds.mod2.controllers;

import eci.cvds.mod2.exceptions.LoanNotFoundException;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.State;
import org.springframework.http.HttpStatus;

import org.springframework.web.server.ResponseStatusException;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class LoanServiceTest {

    @Mock
    private LoanRepo loanRepo;

    @InjectMocks
    private LoanService loanService;

    private Loan sampleLoan;

    @BeforeEach
    void setUp() {
        // Initialize mocks and a sample loan object before each test
        MockitoAnnotations.openMocks(this);
        sampleLoan = new Loan("1", "element123", State.PRESTAMO_PENDIENTE);
    }

    @Test
    void shouldCreateLoan() {
        // Test creating a loan and verify its properties are correctly set
        when(loanRepo.save(any(Loan.class))).thenReturn(sampleLoan);
        Loan created = loanService.createLoan(sampleLoan);
        assertEquals("element123", created.getElementId());
        assertEquals(State.PRESTAMO_PENDIENTE, created.getState());
    }
    @Test
    void shouldThrowExceptionWhenLoanCreationFails() {
        // Test that an exception is thrown if the loan cannot be created
        when(loanRepo.save(any(Loan.class))).thenThrow(new RuntimeException("Database error"));

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loanService.createLoan(sampleLoan);
        });

        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void shouldReturnLoanById() {
        // Test fetching a loan by ID
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        Loan found = loanService.getLoanById("1");
        assertEquals("element123", found.getElementId());
    }
    @Test
    void shouldThrowExceptionWhenLoanByIdNotFound() {
        // Test that an exception is thrown when the loan with the given ID does not exist
        when(loanRepo.findById("999")).thenReturn(Optional.empty());

        // Expect a ResponseStatusException with NOT_FOUND status
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoanById("999");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }



    @Test
    void shouldReturnLoansByState() {
        // Test retrieving loans filtered by their state
        when(loanRepo.findByState(State.PRESTAMO_PENDIENTE)).thenReturn(Arrays.asList(sampleLoan));
        List<Loan> result = loanService.getLoansByState(State.PRESTAMO_PENDIENTE);
        assertEquals(1, result.size());
        assertEquals(State.PRESTAMO_PENDIENTE, result.get(0).getState());
    }
    @Test
    void shouldThrowExceptionWhenNoLoansFoundByState() {
        // Simula que no se encuentran préstamos en el estado dado
        when(loanRepo.findByState(State.PRESTAMO_PENDIENTE)).thenReturn(Arrays.asList()); // Lista vacía

        // Verifica que se lanza una ResponseStatusException
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoansByState(State.PRESTAMO_PENDIENTE);
        });

        // Verifica que el código de estado sea NOT_FOUND (404) en la excepción
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());

        // Verifica que el mensaje de la excepción contenga la razón correcta
        assertTrue(exception.getReason().contains("No loans found for state"));
    }


    @Test
    void shouldUpdateLoan() {
        // Test updating a loan's element ID and state
        Loan updatedLoan = new Loan(null, "element999", State.PRESTAMO_DEVUELTO);
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        when(loanRepo.save(any(Loan.class))).thenAnswer(i -> i.getArgument(0));

        Loan result = loanService.updateLoan("1", updatedLoan);

        assertEquals("element999", result.getElementId());
        assertEquals(State.PRESTAMO_DEVUELTO, result.getState());
    }



    @Test
    void shouldDeleteLoan() {
        // Test deleting a loan by ID and verifying deletion
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        doNothing().when(loanRepo).deleteById("1");

        Loan deleted = loanService.deleteLoan("1");

        assertEquals("1", deleted.getId());
        verify(loanRepo).deleteById("1");
    }
    @Test
    void shouldThrowExceptionWhenLoanNotFoundForDeletion() {
        // Simulate that the loan does not exist (ID 1)
        when(loanRepo.findById("1")).thenReturn(Optional.empty());

        // Verify that a LoanNotFoundException is thrown when trying to delete the loan
        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanService.deleteLoan("1");
        });

        // Verify that the exception message contains the correct reason
        assertTrue(exception.getMessage().contains("Loan not found"));  // Or adjust this message if necessary

        // Verify that deleteById was not called
        verify(loanRepo, times(0)).deleteById("1");
    }

    @Test
    void shouldThrowWhenLoanNotFound() {
        // Test that an exception is thrown when a loan is not found
        when(loanRepo.findById("999")).thenReturn(Optional.empty());

        assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoanById("999");
        });
    }

}

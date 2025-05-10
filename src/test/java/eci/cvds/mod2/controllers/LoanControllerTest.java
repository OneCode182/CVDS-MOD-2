package eci.cvds.mod2.controllers;

import eci.cvds.mod2.exceptions.LoanException;
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
    @Mock
    private ElementsController elementsController;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleLoan = new Loan("1", "element123", State.PRESTAMO_PENDIENTE);
    }

    @Test
    void shouldCreateLoan() {
        when(elementsController.getElementById("element123")).thenReturn(null);
        when(loanRepo.save(any(Loan.class))).thenReturn(sampleLoan);
        Loan created = loanService.createLoan(sampleLoan);
        assertEquals("element123", created.getElementId());
        assertEquals(State.PRESTAMO_PENDIENTE, created.getState());
    }

    @Test
    void shouldThrowExceptionWhenLoanCreationFails() {
        when(elementsController.getElementById("element123")).thenReturn(null);
        when(loanRepo.save(any(Loan.class))).thenThrow(new RuntimeException("Database error"));
        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            loanService.createLoan(sampleLoan);
        });
        assertEquals("Database error", exception.getMessage());
    }

    @Test
    void shouldReturnLoanById() {
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        Loan found = loanService.getLoanById("1");
        assertEquals("element123", found.getElementId());
    }

    @Test
    void shouldThrowExceptionWhenLoanByIdNotFound() {
        when(loanRepo.findById("999")).thenReturn(Optional.empty());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoanById("999");
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }

    @Test
    void shouldReturnLoansByState() {
        when(loanRepo.findByState(State.PRESTAMO_PENDIENTE)).thenReturn(Arrays.asList(sampleLoan));
        List<Loan> result = loanService.getLoansByState(State.PRESTAMO_PENDIENTE);
        assertEquals(1, result.size());
        assertEquals(State.PRESTAMO_PENDIENTE, result.get(0).getState());
    }

    @Test
    void shouldThrowExceptionWhenNoLoansFoundByState() {
        when(loanRepo.findByState(State.PRESTAMO_PENDIENTE)).thenReturn(Arrays.asList());
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoansByState(State.PRESTAMO_PENDIENTE);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("No loans found for state"));
    }

    @Test
    void shouldUpdateLoan() {
        Loan updatedLoan = new Loan(null, "element999", State.PRESTAMO_DEVUELTO);
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        when(elementsController.getElementById("element999")).thenReturn(null);
        when(loanRepo.save(any(Loan.class))).thenAnswer(i -> i.getArgument(0));
        Loan result = loanService.updateLoan("1", updatedLoan);
        assertEquals("element999", result.getElementId());
        assertEquals(State.PRESTAMO_DEVUELTO, result.getState());
    }

    @Test
    void shouldDeleteLoan() {
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        doNothing().when(loanRepo).deleteById("1");
        Loan deleted = loanService.deleteLoan("1");
        assertEquals("1", deleted.getId());
        verify(loanRepo).deleteById("1");
    }

    @Test
    void shouldThrowExceptionWhenLoanNotFoundForDeletion() {
        when(loanRepo.findById("1")).thenReturn(Optional.empty());
        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanService.deleteLoan("1");
        });
        assertTrue(exception.getMessage().contains("Loan not found"));
        verify(loanRepo, times(0)).deleteById("1");
    }

    @Test
    void shouldThrowWhenLoanNotFound() {
        when(loanRepo.findById("999")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> {
            loanService.getLoanById("999");
        });
    }

    @Test
    void shouldNotCreateLoanWithNullElementId() {
        Loan invalidLoan = new Loan("2", null, State.PRESTAMO_PENDIENTE);
        when(loanRepo.save(any(Loan.class))).thenThrow(new IllegalArgumentException("elementId is required"));
        IllegalArgumentException exception = assertThrows(IllegalArgumentException.class, () -> {
            loanService.createLoan(invalidLoan);
        });
        assertEquals("elementId is required", exception.getMessage());
    }

    @Test
    void shouldNotCreateLoanWithInvalidState() {
        Loan invalidLoan = new Loan("2", "element456", null);
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.createLoan(invalidLoan);
        });
        assertEquals(HttpStatus.BAD_REQUEST, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Invalid state value"));
    }

    @Test
    void shouldNotUpdateNonExistentLoan() {
        Loan update = new Loan("2", "element456", State.PRESTAMO_DEVUELTO);
        when(loanRepo.findById("2")).thenReturn(Optional.empty());
        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanService.updateLoan("2", update);
        });
        assertEquals(LoanException.LOAN_NOT_FOUND, exception.getMessage());
    }

    @Test
    void shouldChangeStateToDamageLoanOnReturn() {
        Loan damagedLoan = new Loan("3", "element999", State.DAMAGE_LOAN);
        when(loanRepo.findById("3")).thenReturn(Optional.of(damagedLoan));
        when(loanRepo.save(any(Loan.class))).thenAnswer(invocation -> invocation.getArgument(0));
        Loan result = loanService.updateLoan("3", damagedLoan);
        assertEquals(State.DAMAGE_LOAN, result.getState());
    }

    @Test
    void shouldFailIfDeletingLoanWithNonExistentId() {
        String nonExistentLoanId = "999"; // ID de tipo String que no existe en la base de datos
        LoanNotFoundException exception = assertThrows(LoanNotFoundException.class, () -> {
            loanService.deleteLoan(nonExistentLoanId);
        });
        assertEquals("Loan not found", exception.getMessage());
        verify(loanRepo, times(1)).findById(nonExistentLoanId); // Verificar la llamada al repositorio
        verify(loanRepo, times(0)).deleteById(nonExistentLoanId); // No debe haberse llamado al método delete
    }



    @Test
    void shouldThrowExceptionIfElementDoesNotExistOnCreate() {
        // Should throw if the element ID is not found during creation
        Loan loanWithInvalidElement = new Loan("4", "element404", State.PRESTAMO_PENDIENTE);
        when(elementsController.getElementById("element404")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.createLoan(loanWithInvalidElement);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
        assertTrue(exception.getReason().contains("Element not found"));
    }

    @Test
    void shouldThrowExceptionIfElementDoesNotExistOnUpdate() {
        // Should throw if the new element ID is invalid during update
        Loan updatedLoan = new Loan("1", "nonexistentElement", State.PRESTAMO_PENDIENTE);
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        when(elementsController.getElementById("nonexistentElement")).thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "Element not found"));
        ResponseStatusException exception = assertThrows(ResponseStatusException.class, () -> {
            loanService.updateLoan("1", updatedLoan);
        });
        assertEquals(HttpStatus.NOT_FOUND, exception.getStatusCode());
    }
    @Test
    void shouldAllowUpdateLoanWithNullElementIdIfNoValidationExists() {
        // Arrange
        when(loanRepo.findById("1")).thenReturn(Optional.of(sampleLoan));
        Loan update = new Loan("1", null, State.PRESTAMO_DEVUELTO);
        when(loanRepo.save(any())).thenAnswer(invocation -> invocation.getArgument(0));

        // Act
        Loan result = loanService.updateLoan("1", update);

        // Assert
        assertNull(result.getElementId());
        assertEquals(State.PRESTAMO_DEVUELTO, result.getState());
    }


}

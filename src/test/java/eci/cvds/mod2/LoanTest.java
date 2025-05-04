package eci.cvds.mod2;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.when;

import java.util.List;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import eci.cvds.mod2.controllers.LoanController;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.State;

@SpringBootTest
public class LoanTest {

    @Mock
    private LoanService loanService;

    @InjectMocks
    private LoanController loanController;

    @Test
    void testGetLoanById() {
        String loanId = "23";
        Loan loan = new Loan(loanId, "1", State.PRESTAMO_DEVUELTO);

        when(loanService.getLoanById(loanId).get()).thenReturn(loan);

        ResponseEntity<Loan> response = loanController.getLoanById(loanId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loan, response.getBody());
    }

    @Test
    void testGetLoanById_NotFound() {
        String loanId = "1";
        when(loanService.getLoanById(loanId).get()).thenReturn(null);
        ResponseEntity<Loan> response = loanController.getLoanById(loanId);

        assertEquals(HttpStatus.NOT_FOUND, response.getStatusCode());
        assertNull(response.getBody());
    }

    @Test
    void testGetLoansByState() {

        State stateLoan = State.PRESTAMO_DEVUELTO;
        Loan loan = new Loan("23", "1", stateLoan);

        List<Loan> loans = List.of(loan);

        when(loanService.getLoansByState(stateLoan)).thenReturn(loans);

        List<Loan> result = loanController.getLoansByState(stateLoan);

        assertEquals(1, result.size());
        assertEquals(loans, result);
    }

    @Test
    void testCreateLoan() {
        String loanId = "23";
        Loan loan = new Loan(loanId, "1", State.PRESTAMO_DEVUELTO);

        when(loanService.createLoan(loan)).thenReturn(loan);

        ResponseEntity<Loan> response = loanController.createLoan(loan);

        assertEquals(HttpStatus.CREATED, response.getStatusCode());
        assertEquals(loan, response.getBody());
    }

    @Test
    void testUpdateLoan() {
        String loanId = "23";
        Loan loan = new Loan(loanId, "1", State.PRESTAMO_DEVUELTO);
        when(loanService.createLoan(loan)).thenReturn(loan);
        loan.setState(State.PRESTAMO_PENDIENTE);
        when(loanService.updateLoan(loanId, loan)).thenReturn(loan);

        ResponseEntity<Loan> response = loanController.updateLoan(loanId, loan);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(loan, response.getBody());
    }

    @Test
    void testDeleteLoan_Success() {
        String loanId = "23";

        when(loanService.deleteLoan(loanId)).thenReturn(true);

        ResponseEntity<?> response = loanController.deleteLoan(loanId);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Loan successfully deleted", response.getBody());
    }

    @Test
    void testDeleteLoan_Failure() {
        String loanId = "1";

        when(loanService.deleteLoan(loanId)).thenReturn(false);

        ResponseEntity<?> response = loanController.deleteLoan(loanId);

        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        assertNull(response.getBody());
    }
}

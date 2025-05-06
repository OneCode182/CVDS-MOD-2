package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.modules.Room;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import eci.cvds.mod2.util.Date;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.ArgumentCaptor;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class LoanServiceTest {

    @Mock
    private LoanRepo loanRepo;

    @InjectMocks
    private LoanService loanService;

    private Reservation buildReservation() {
        Room room = new Room("room1", "Sala 1", "Bloque A", 10, 0);
        return new Reservation(
                "res1",
                "Juan",
                "user1",
                Role.STUDENT,
                new Date(),
                "room1",
                "Bloque A",
                null,
                null,
                State.RESERVA_CONFIRMADA,
                2,
                false,
                room
        );
    }

    @Test
    public void testCreateLoan() {
        Loan loan = new Loan(null, "element123", State.PRESTAMO_PENDIENTE, buildReservation());
        Loan savedLoan = new Loan("loan1", "element123", State.PRESTAMO_PENDIENTE, loan.getReservation());

        when(loanRepo.save(any(Loan.class))).thenReturn(savedLoan);

        Loan result = loanService.createLoan(loan);

        assertNotNull(result.getId());
        assertEquals("element123", result.getElementId());
        assertEquals(State.PRESTAMO_PENDIENTE, result.getState());
    }

    @Test
    public void testGetLoanById() {
        Loan loan = new Loan("loan1", "element123", State.PRESTAMO_PENDIENTE, buildReservation());

        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));

        Optional<Loan> result = loanService.getLoanById("loan1");

        assertTrue(result.isPresent());
        assertEquals("loan1", result.get().getId());
    }

    @Test
    public void testUpdateLoan() {
        Loan existingLoan = new Loan("loan1", "element123", State.PRESTAMO_PENDIENTE, buildReservation());
        Loan updatedLoan = new Loan("loan1", "element123", State.PRESTAMO_DEVUELTO, existingLoan.getReservation());
        when(loanRepo.findById("loan1")).thenReturn(Optional.of(existingLoan));
        ArgumentCaptor<Loan> loanCaptor = ArgumentCaptor.forClass(Loan.class);
        when(loanRepo.save(any(Loan.class))).thenReturn(updatedLoan);
        Loan result = loanService.updateLoan("loan1", updatedLoan);
        verify(loanRepo, times(1)).save(loanCaptor.capture());
        Loan capturedLoan = loanCaptor.getValue();
        assertEquals(State.PRESTAMO_DEVUELTO, capturedLoan.getState());
        assertEquals("loan1", capturedLoan.getId());
        assertEquals("element123", capturedLoan.getElementId());
        verify(loanRepo, times(1)).save(updatedLoan);
    }


    @Test
    public void testDeleteLoan() {
        String loanId = "loan1";

        doNothing().when(loanRepo).deleteById(loanId);

        loanService.deleteLoan(loanId);

        verify(loanRepo, times(1)).deleteById(loanId);
    }
}

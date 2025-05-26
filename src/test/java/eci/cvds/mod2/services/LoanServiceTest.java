package eci.cvds.mod2.services;

import eci.cvds.mod2.exceptions.LoanNotFoundException;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.web.server.ResponseStatusException;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class LoanServiceTest {

    @Mock
    private LoanRepo loanRepo;

    @Mock
    private ElementsService elementsService;

    @Mock
    private ReservationService reservationService;

    @InjectMocks
    private LoanService loanService;

    private Loan loan;

    @BeforeEach
    void setUp() {
        loan = new Loan ("loan1","element1",State.PRESTAMO_DEVUELTO,"rev1");

    }

    @Test
    void shouldGetLoanById() {
        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));
        Loan result = loanService.getLoanById("loan1");
        assertEquals("loan1", result.getId());
    }

    @Test
    void shouldThrowWhenLoanNotFoundById() {
        when(loanRepo.findById("loan1")).thenReturn(Optional.empty());
        assertThrows(ResponseStatusException.class, () -> loanService.getLoanById("loan1"));
    }

    @Test
    void shouldGetLoansByState() {
        when(loanRepo.findByState(State.PRESTAMO_DEVUELTO)).thenReturn(List.of(loan));
        List<Loan> result = loanService.getLoansByState(State.PRESTAMO_DEVUELTO);
        assertEquals(1, result.size());
    }

    @Test
    void shouldThrowWhenNoLoansByState() {
        when(loanRepo.findByState(State.PRESTAMO_DEVUELTO)).thenReturn(Collections.emptyList());
        assertThrows(ResponseStatusException.class, () -> loanService.getLoansByState(State.PRESTAMO_DEVUELTO));
    }

    @Test
    void shouldCreateLoanSuccessfully() {
        Loan loan1 = new Loan("loan1", "element1", State.PRESTAMO_PENDIENTE, "rev1");

        when(loanRepo.save(loan1)).thenReturn(loan1);
        RecreationalElement element = new RecreationalElement("element1","juego",1,"fun");
        when(elementsService.getElementById("element1")).thenReturn(element);
        doNothing().when(elementsService).reduceElementQuantity(eq("element1"), eq(1));
        when(reservationService.getReservationById("rev1")).thenReturn(mock(Reservation.class));

        Loan result = loanService.createLoan(loan1);

        assertNotNull(result);
        verify(loanRepo).save(loan1);
    }


    @Test
    void shouldUpdateLoanSuccessfully() {
        Loan updated = new Loan("1","element1",State.PRESTAMO_PENDIENTE,"rev2");


        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));
        when(elementsService.getElementById("element1")).thenReturn(new RecreationalElement());
        when(reservationService.getReservationById("rev1")).thenReturn(mock(Reservation.class));
        when(loanRepo.save(any())).thenReturn(loan);

        Loan result = loanService.updateLoan("loan1", updated);
        assertEquals(State.PRESTAMO_PENDIENTE, result.getState());
        verify(loanRepo).save(loan);
    }

    @Test
    void shouldDeleteLoanSuccessfully() {
        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));
        doNothing().when(loanRepo).deleteById("loan1");

        Loan result = loanService.deleteLoan("loan1");

        assertNotNull(result);
        assertEquals("loan1", result.getId());
        verify(loanRepo).deleteById("loan1");
    }

    @Test
    void shouldChangeLoanStateToReturnedAndIncreaseElement() {
        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));
        when(loanRepo.save(any())).thenReturn(loan);

        doNothing().when(elementsService).increaseElementQuantity("element1", 1);

        loanService.changeLoanState("loan1", State.PRESTAMO_DEVUELTO);

        verify(elementsService).increaseElementQuantity("element1", 1);
        verify(loanRepo).save(loan);
    }

    @Test
    void shouldThrowOnInvalidStateChange() {
        when(loanRepo.findById("loan1")).thenReturn(Optional.of(loan));
        assertThrows(ResponseStatusException.class, () -> loanService.changeLoanState("loan1", null));
    }

    @Test
    void shouldReturnAllLoans() {
        when(loanRepo.findAll()).thenReturn(List.of(loan));
        List<Loan> result = loanService.getAll();
        assertEquals(1, result.size());
    }
}

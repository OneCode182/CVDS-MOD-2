package eci.cvds.mod2.controllers;


import com.fasterxml.jackson.databind.ObjectMapper;
import eci.cvds.mod2.exceptions.*;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.services.ElementsService;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.GlobalExceptionHandler;
import eci.cvds.mod2.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;

import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.*;
import org.springframework.web.server.ResponseStatusException;


import java.util.Collections;
import java.util.List;

import static org.mockito.Mockito.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;

@WebMvcTest(LoanControllerTest.class)
class LoanControllerTest {

    @Mock
    private LoanService loanService;
    @InjectMocks
    private LoanController loanController;
    private Loan sampleLoan;
    @Mock
    private ElementsService elementsService;
    @Autowired
    private MockMvc mockMvc;
    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        sampleLoan = new Loan("1", "element123", State.PRESTAMO_PENDIENTE, "reserva123");
        mockMvc = MockMvcBuilders
                .standaloneSetup(loanController)
                .setControllerAdvice(new GlobalExceptionHandler())
                .build();
    }
    @Test
    void shouldReturn200WhenGettingLoanWithId() throws  Exception{
        when(loanService.getLoanById("1")).thenReturn(sampleLoan);
        mockMvc.perform(get("/loans/id/1")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingLoanWithId() throws Exception{
        when(loanService.getLoanById("11"))
                .thenThrow( new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        mockMvc.perform(get("/loans/id/11")).andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn200WhenGettingLoansWithState() throws  Exception{
        List<Loan> loans = Collections.singletonList(sampleLoan);
        when(loanService.getLoansByState(State.PRESTAMO_PENDIENTE)).thenReturn(loans);
        mockMvc.perform(get("/loans/state/PRESTAMO_PENDIENTE")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn404WhenGettingNonExistingLoanWithThatState() throws Exception{
        State state = State.PRESTAMO_DEVUELTO;
        when(loanService.getLoansByState(State.PRESTAMO_DEVUELTO))
                .thenThrow(new ResponseStatusException(HttpStatus.NOT_FOUND, "No loans found for state: " + state));
        mockMvc.perform(get("/loans/state/PRESTAMO_DEVUELTO")).andExpect(status().isNotFound());
    }
    @Test
    void shouldReturn201WhenCorrectCreationOfLoan() throws Exception {
        Loan loan = new Loan("12","123",State.PRESTAMO_PENDIENTE,"321");
        when(loanService.createLoan(any(Loan.class))).thenReturn(loan);
        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loan)))
                .andExpect(status().isCreated())
                .andExpect(content().string("Loan successfully created"));
        verify(loanService).createLoan(any(Loan.class));
    }
    @Test
    void shouldReturn400WhenCreatingALoanWithANonValidState() throws Exception{
        Loan loan = mock(Loan.class);
        when(loan.getState()).thenReturn(null);
        when(loanService.createLoan(any(Loan.class)))
                .thenThrow(new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state value"));
        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loan)))
                .andExpect(status().isBadRequest());
        verify(loanService).createLoan(any(Loan.class));
    }
    @Test
    void shouldReturn404WhenCreatingALoanWithNonExistingElement() throws Exception {
        Loan loan = mock(Loan.class);
        when(elementsService.getElementById(loan.getId()))
                .thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        when(loanService.createLoan(any(Loan.class)))
                .thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loan)))
                .andExpect(status().isNotFound());
        verify(loanService).createLoan(any(Loan.class));
    }
    @Test
    void shouldReturn404WhenCreatingALoanWithNonExistingReservation() throws Exception {
        Loan loan = mock(Loan.class);

        when(loanService.createLoan(any(Loan.class)))
                .thenThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        mockMvc.perform(post("/loans")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(loan)))
                .andExpect(status().isNotFound());
        verify(loanService).createLoan(any(Loan.class));
    }
    @Test
    void shouldReturn200WhenUpdatingLoan() throws Exception {
        when (loanService.updateLoan(eq("1"),any(Loan.class))).thenReturn(sampleLoan);
        mockMvc.perform(put("/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleLoan)))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan successfully updated "));
        verify(loanService).updateLoan(eq("1"),any(Loan.class));
    }
    @Test
    void shouldReturn404WhenUpdatingNotExistentLoan() throws Exception {
        when(loanService.updateLoan(eq("1"),any(Loan.class)))
                .thenThrow(new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        mockMvc.perform(put("/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleLoan)))
                .andExpect(status().isNotFound());
        verify(loanService).updateLoan(eq("1"),any(Loan.class));
    }
    @Test
    void shouldReturn404WhenUpdatingLoanWithNonExistentElement() throws Exception {
        when(loanService.updateLoan(eq("1"),any(Loan.class)))
                .thenThrow(new ElementNotFoundException(ElementException.ELEMENT_NOT_FOUND));
        mockMvc.perform(put("/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleLoan)))
                .andExpect(status().isNotFound());
        verify(loanService).updateLoan(eq("1"),any(Loan.class));
    }
    @Test
    void shouldReturn404WhenUpdatingLoanWithNonExistentReservation() throws Exception {
        when(loanService.updateLoan(eq("1"),any(Loan.class)))
                .thenThrow(new ReservationNotFoundException(ReservationException.REV_NOT_FOUND));
        mockMvc.perform(put("/loans/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(new ObjectMapper().writeValueAsString(sampleLoan)))
                .andExpect(status().isNotFound());
        verify(loanService).updateLoan(eq("1"),any(Loan.class));
    }
    @Test
    void shouldReturn200WhenDeletingElement() throws Exception {
        when(loanService.deleteLoan("1")).thenReturn(sampleLoan);
        mockMvc.perform(delete("/loans/1"))
                .andExpect(status().isOk())
                .andExpect(content().string("Loan successfully deleted"));
        verify(loanService).deleteLoan("1");
    }
    @Test
    void shouldReturn200WhenGettingAllElements() throws Exception{
        List<Loan> loans = Collections.singletonList(sampleLoan);
        when(loanService.getAll()).thenReturn(loans);
        mockMvc.perform(get("/loans")).andExpect(status().isOk());
    }
    @Test
    void shouldReturn200WhenChangingALoanStatus() throws Exception{
        doNothing().when(loanService).changeLoanState("1",State.PRESTAMO_PENDIENTE);
        mockMvc.perform(put("/loans/state/1/PRESTAMO_PENDIENTE")).andExpect(status().isOk());
    }

}

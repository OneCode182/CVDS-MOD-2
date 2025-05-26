package eci.cvds.mod2.services;

import eci.cvds.mod2.controllers.ElementsController;
import eci.cvds.mod2.exceptions.LoanException;
import eci.cvds.mod2.exceptions.LoanNotFoundException;
import eci.cvds.mod2.exceptions.ReservationException;
import eci.cvds.mod2.exceptions.ReservationNotFoundException;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.modules.RecreationalElement;
import eci.cvds.mod2.modules.Reservation;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class LoanService {
    private final LoanRepo loanRepo;
    private final ElementsService elementsService;
    private final ReservationService reservationService;
    @Autowired
    public LoanService(LoanRepo loanRepo, ElementsService elementsController, ReservationService reservationService){
        this.loanRepo=loanRepo;
        this.elementsService = elementsController;
        this.reservationService = reservationService;
    }


    public Loan getLoanById(String loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, LoanException.LOAN_NOT_FOUND));
    }

    public List<Loan> getLoansByState(State state) {
        List<Loan> loans = loanRepo.findByState(state);
        if (loans.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "No loans found for state: " + state);
        }
        return loans;
    }


    public Loan createLoan(Loan loan) {
        if (!State.isValidState(loan.getState())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state value");
        }
        reservationService.getReservationById(loan.getRevId());
        RecreationalElement element = elementsService.getElementById(loan.getElementId());
        elementsService.reduceElementQuantity(element.getId(), 1);
        return loanRepo.save(loan);
    }

    public Loan updateLoan(String loanId, Loan newLoan) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        elementsService.getElementById(newLoan.getElementId());
        reservationService.getReservationById(loan.getRevId());
        loan.setRevId(newLoan.getRevId());
        loan.setElementId(newLoan.getElementId());
        loan.setState(newLoan.getState());
        return loanRepo.save(loan);
    }
    public Loan deleteLoan(String loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException("Loan not found"));
        loanRepo.deleteById(loanId);
        return loan;
    }
    public void changeLoanState(String loanId, State state){
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(()-> new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        if(!State.isValidState(state)){
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state value");
        }
        loan.setState(state);
        if(state.equals(State.PRESTAMO_DEVUELTO)){
            elementsService.increaseElementQuantity(loan.getElementId(),1);
        }
        loanRepo.save(loan);
    }

    public List<Loan> getAll() {
        return loanRepo.findAll();
    }

}

package eci.cvds.mod2.services;

import eci.cvds.mod2.controllers.ElementsController;
import eci.cvds.mod2.exceptions.LoanException;
import eci.cvds.mod2.exceptions.LoanNotFoundException;
import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
@Service
public class LoanService {
    private LoanRepo loanRepo;
    private ElementsController elementsController;
    @Autowired
    public LoanService(LoanRepo loanRepo, ElementsController elementsController){
        this.loanRepo=loanRepo;
        this.elementsController = elementsController;
    }


    public Loan getLoanById(String loanId) {
        return loanRepo.findById(loanId)
                .orElseThrow(()-> new ResponseStatusException(HttpStatus.NOT_FOUND, LoanException.LOAN_NOT_FOUND));
    }

    public List<Loan> getLoansByState(State state) {
        return loanRepo.findByState(state);
    }

    public Loan createLoan(Loan loan) {
        if (!State.isValidState(loan.getState())) {
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Invalid state value");
        }
        elementsController.getElementById(loan.getElementId());
        return loanRepo.save(loan);
    }

    public Loan updateLoan(String loanId, Loan newLoan) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(() -> new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        elementsController.getElementById(newLoan.getElementId());
        loan.setElementId(newLoan.getElementId());
        loan.setState(newLoan.getState());
        return loanRepo.save(loan);
    }

    public Loan deleteLoan(String loanId) {
        Loan loan = loanRepo.findById(loanId)
                .orElseThrow(()-> new LoanNotFoundException(LoanException.LOAN_NOT_FOUND));
        loanRepo.deleteById(loanId);
        return loan;
    }

    public List<Loan> getAll() {
        return loanRepo.findAll();
    }
}

package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.services.LoanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "*")
public class LoanController {
    LoanService loanService;
    @Autowired
    public LoanController(LoanService loanService){
        this.loanService=loanService;
    }
    public ResponseEntity<Loan> getLoanById(String loanId) {
        return null;
    }

    public List<Loan> getLoansByState(boolean state) {
        return null;
    }

    public ResponseEntity<Loan> createLoan(Loan loan) {
        return null;
    }

    public ResponseEntity<Loan> updtateLoan(Loan loan, Loan newLoan) {
        return null;
    }

    public ResponseEntity<Loan> deleteLoan(String loanId) {
        return null;
    }

    public List<Loan> getAll() {
        return null;
    }
}

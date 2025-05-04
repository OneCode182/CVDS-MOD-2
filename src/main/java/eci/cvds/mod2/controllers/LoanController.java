package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.State;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/loans")
@CrossOrigin(origins = "*")
public class LoanController {
    LoanService loanService;

    @Autowired
    public LoanController(LoanService loanService) {
        this.loanService = loanService;
    }

    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
        Optional<Loan> loan = loanService.getLoanById(loanId);
        if (loan.isPresent()) {
            return ResponseEntity.ok(loan.get());
        } else {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/state/{state}")
    public List<Loan> getLoansByState(@PathVariable State state) {
        return loanService.getLoansByState(state);
    }

    @PostMapping
    public ResponseEntity<Loan> createLoan(@RequestBody Loan loan) {
        Loan newLoan = loanService.createLoan(loan);
        if (newLoan != null) {
            return ResponseEntity.status(HttpStatus.CREATED).body(newLoan);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

    @PutMapping("/{loanId}")
    public ResponseEntity<Loan> updateLoan(@PathVariable String loanId, @RequestBody Loan newLoan) {
        Loan updatedLoan = loanService.updateLoan(loanId, newLoan);
        if (updatedLoan != null) {
            return ResponseEntity.ok(updatedLoan);
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }

    }

    @DeleteMapping("/{loanId}")
    public ResponseEntity<?> deleteLoan(@PathVariable String loanId) {
        boolean deleted = loanService.deleteLoan(loanId);
        if (deleted) {
            return ResponseEntity.ok().body("Loan successfully deleted");
        } else {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(null);
        }
    }

}

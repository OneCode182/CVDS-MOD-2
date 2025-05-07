package eci.cvds.mod2.controllers;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.services.LoanService;
import eci.cvds.mod2.util.State;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
    @GetMapping("/id/{loanId}")
    public ResponseEntity<Loan> getLoanById(@PathVariable String loanId) {
        Loan loan = loanService.getLoanById(loanId);
        return ResponseEntity.ok(loan);
    }
    @GetMapping("/state/{state}")
    public List<Loan> getLoansByState(@PathVariable State state) {
        return loanService.getLoansByState(state);
    }
    @PostMapping
    public ResponseEntity<String> createLoan(@Valid @RequestBody Loan loan) {
        loanService.createLoan(loan);
        return ResponseEntity.status(HttpStatus.CREATED).body("Loan successfully created");
    }
    @PutMapping("/{loanId}")
    public ResponseEntity<String> updateLoan(@PathVariable String loanId, @Valid @RequestBody Loan newLoan) {
        loanService.updateLoan(loanId,newLoan);
        return ResponseEntity.ok("Loan successfully updated ");
    }
    @DeleteMapping("/{loanId}")
    public ResponseEntity<String> deleteLoan(@PathVariable String loanId) {
        loanService.deleteLoan(loanId);
        return ResponseEntity.ok("Loan successfully deleted");
    }
}

package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class LoanService {
    private LoanRepo loanRepo;
    @Autowired
    public LoanService(LoanRepo loanRepo){
        this.loanRepo=loanRepo;
    }


    public Loan getLoanById(String loanId) {
        return null;
    }

    public List<Loan> getLoansByState(State state) {
        return null;
    }

    public Loan createLoan(Loan loan) {
        return null;
    }

    public Loan updtateLoan(String loanId, Loan newLoan) {
        return null;
    }

    public Loan deleteLoan(String loanId) {
        return null;
    }

    public List<Loan> getAll() {
        return null;
    }
}

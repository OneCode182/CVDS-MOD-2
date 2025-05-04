package eci.cvds.mod2.services;

import eci.cvds.mod2.modules.Loan;
import eci.cvds.mod2.reposistories.LoanRepo;
import eci.cvds.mod2.util.State;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LoanService {
    private LoanRepo loanRepo;

    @Autowired
    public LoanService(LoanRepo loanRepo) {
        this.loanRepo = loanRepo;
    }

    public Optional<Loan> getLoanById(String loanId) {
        return loanRepo.findById(loanId);
    }

    public List<Loan> getLoansByState(State state) {
        return loanRepo.findByState(state);
    }

    public Loan createLoan(Loan loan) {
        try {
            Loan savedLoan = loanRepo.save(loan);
            return savedLoan;
        } catch (Exception e) {
            return null;
        }
    }

    public Loan updateLoan(String loanId, Loan loan) {
        if (!loanRepo.existsById(loanId)) {
            return null;
        }
        Loan updatedLoan = loanRepo.findById(loanId).orElse(null);
        if (updatedLoan == null) {
            return null;
        }
        updatedLoan.setElementId(loan.getElementId());
        updatedLoan.setState(loan.getState());
        return loanRepo.save(updatedLoan);
    }

    public boolean deleteLoan(String loanId) {
        try {
            loanRepo.deleteById(loanId);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    public List<Loan> getAll() {
        return loanRepo.findAll();
    }
}

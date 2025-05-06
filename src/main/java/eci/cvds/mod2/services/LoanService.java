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
        return loanRepo.findAll().stream()
                .filter(loan -> loan.getState().equals(state))
                .toList();
    }

    public Loan createLoan(Loan loan) {
        try {
            Loan savedLoan = loanRepo.save(loan);
            return savedLoan;
        } catch (Exception e) {
            return null;
        }
    }
  
  public Loan updateLoan(String loanId, Loan newLoan) {
        Optional<Loan> existingLoanOpt = loanRepo.findById(loanId);
        if (existingLoanOpt.isPresent()) {
            Loan existingLoan = existingLoanOpt.get();
            existingLoan.setState(newLoan.getState());
            existingLoan.setElementId(newLoan.getElementId());

            return loanRepo.save(existingLoan);
        } else {
            throw new RuntimeException("Loan not found");
        }
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
  
    public List<Loan> getLoansByRoomId(String roomId) {
        return loanRepo.findAll().stream()
                .filter(loan -> loan.getReservation() != null &&
                        loan.getReservation().getRoom() != null &&
                        loan.getReservation().getRoom().getRoomId().equals(roomId))
                .toList();
    }

    public List<Loan> getLoansWithDamagedElements() {
        return loanRepo.findAll().stream()
                .filter(loan -> loan.getState() == State.DAMAGED_ELEMENT)
                .toList();

    }
}

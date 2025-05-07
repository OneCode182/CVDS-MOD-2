package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Document(collection = "reservations")
@Builder
public class Reservation {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    @Setter
    private String userName;

    @Setter
    private String userId;

    @Setter
    private Role role;

    @Setter
    private Date date;

    @Setter
    private String roomId;

    @Builder.Default
    private Set<String> loans = new HashSet<>();

    @Setter
    private State state;

    @Setter
    private int people;

    public void addLoan(String loanId) {
        if (loans == null) {
            this.loans = new HashSet<>();
        }
        this.loans.add(loanId);
    }
    public void setLoans(Set<String> loans) {
        if (loans == null) {
            this.loans = new HashSet<>();
        } else {
            this.loans = new HashSet<>(loans);
        }
    }

    public Set<String> getLoans() {
        return Collections.unmodifiableSet(loans != null ? loans : new HashSet<>());
    }

}

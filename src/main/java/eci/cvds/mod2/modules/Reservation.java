package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.*;

@Getter
@Setter
@Document(collection = "reservations")
@AllArgsConstructor
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

    @Builder.Default
    private Set<String> recreationalElements = new HashSet<>();

    @Setter
    private State state;

    @Setter
    private int people;

    public void addLoan(String loanId) {
        this.loans.add(loanId);
    }

    public Set<String> getLoans() {
        return Collections.unmodifiableSet(loans);
    }
}

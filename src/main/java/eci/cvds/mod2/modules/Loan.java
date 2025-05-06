package eci.cvds.mod2.modules;

import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Objects;

@Document(collection = "loans")
public class Loan {

    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    private String id;

    private String elementId;
    private State state;
    private Reservation reservation;

    public Loan() {}

    public Loan(String id, String elementId, State state, Reservation reservation) {
        this.id = id;
        this.elementId = elementId;
        this.state = state;
        this.reservation = reservation;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getElementId() {
        return elementId;
    }

    public void setElementId(String elementId) {
        this.elementId = elementId;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public Reservation getReservation() {
        return reservation;
    }

    public void setReservation(Reservation reservation) {
        this.reservation = reservation;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Loan loan = (Loan) o;
        return Objects.equals(id, loan.id) &&
                Objects.equals(elementId, loan.elementId) &&
                state == loan.state &&
                Objects.equals(reservation, loan.reservation);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, elementId, state, reservation);
    }

}

// package where the module are
package eci.cvds.mod2.modules;
// imports needed to do reservations
import com.fasterxml.jackson.annotation.JsonProperty;
import eci.cvds.mod2.util.Date;
import eci.cvds.mod2.util.Role;
import eci.cvds.mod2.util.State;
import org.springframework.data.annotation.Id;
import org.springframework.data.mongodb.core.mapping.Document;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;
// USe mongo database
@Document(collection = "reservations")
//class who have all the attributes from the model reservation
public class Reservation {
    @Id
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    // User ID
    private String id;
    // User name
    private String userName;
    // User identification
    private String userId;
    // User role
    private Role role;
    // Date for the reservation
    private Date date;
    // Room where the user did reservation
    private String roomId;
    // Build where user did the reservation
    private String location;
    // identification of loans that user can reserve
    private Set<String> loans;
    // elements that user can reserve
    private Set<String> recreationalElements;
    // state of the reservation
    private State state;
    // number of people in the reservation
    private int people;
    // Status of the returned loan element
    private boolean elementsReturned;
    // Constructor that use all attributes for create a reservation and assigned everything
    public Reservation(String id, String userName, String userId, Role role, Date date,
                       String roomId, String location, Set<String> loans,
                       Set<String> recreationalElements, State state, int people,
                       boolean elementsReturned) {
        this.id = id;
        this.userName = userName;
        this.userId = userId;
        this.role = role;
        this.date = date;
        this.roomId = roomId;
        this.location = location;
        this.loans = loans != null ? new HashSet<>(loans) : new HashSet<>();
        this.recreationalElements = recreationalElements != null ? new HashSet<>(recreationalElements) : new HashSet<>();
        this.state = state;
        this.people = people;
        this.elementsReturned = elementsReturned;
    }
    // Empty constructor to initialize the attributes of loans and recreationelements
    public Reservation() {
        this.loans = new HashSet<>();
        this.recreationalElements = new HashSet<>();
    }
    // Getters and Setters for the module
    public String getId() {
        return id;
    }
    public String getUserName() {
        return userName;
    }
    public void setUserName(String userName) {
        this.userName = userName;
    }
    public String getUserId() {
        return userId;
    }
    public void setUserId(String userId) {
        this.userId = userId;
    }
    public Role getRole() {
        return role;
    }
    public void setRole(Role role) {
        this.role = role;
    }
    public Date getDate() {
        return date;
    }
    public void setDate(Date date) {
        this.date = date;
    }
    public String getRoomId() {
        return roomId;
    }
    public void setRoomId(String roomId) {
        this.roomId = roomId;
    }
    public String getLocation() {
        return location;
    }
    public void setLocation(String location) {
        this.location = location;
    }
    public Set<String> getLoans() {
        return Collections.unmodifiableSet(loans);
    }
    public void setLoans(Set<String> loans) {
        this.loans = loans != null ? new HashSet<>(loans) : new HashSet<>();
    }
    public Set<String> getRecreationalElements() {
        return Collections.unmodifiableSet(recreationalElements);
    }
    public void setRecreationalElements(Set<String> recreationalElements) {
        this.recreationalElements = recreationalElements != null ? new HashSet<>(recreationalElements) : new HashSet<>();
    }
    public State getState() {
        return state;
    }
    public void setState(State state) {
        this.state = state;
    }
    public int getPeople() {
        return people;
    }
    public void setPeople(int people) {
        this.people = people;
    }
    public boolean isElementsReturned() {
        return elementsReturned;
    }
    public void setElementsReturned(boolean elementsReturned) {
        this.elementsReturned = elementsReturned;
    }
    // methods used by class
    public void addLoan(String loanId) {
        this.loans.add(loanId);
    }
    public void addRecreationalElement(String element) {
        this.recreationalElements.add(element);
    }
    public void markElementsAsReturned() {
        this.elementsReturned = true;
    }
}
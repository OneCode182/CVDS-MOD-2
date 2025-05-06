package eci.cvds.mod2.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class ReservationTest {

    private Reservation reservation;

    // Setup method to initialize the Reservation object before each test
    @BeforeEach
    void setUp() {
        reservation = new Reservation();  // Create a new Reservation object
    }

    // Teardown method to clean up after each test (if necessary)
    @AfterEach
    void tearDown() {
        // Clean up resources if necessary
    }

    // Test to ensure that the Reservation object is initialized correctly
    @Test
    void testReservationInitialization() {
        assertNotNull(reservation, "The reservation object should be initialized");
    }

    // Test to verify adding a loan to the reservation
    @Test
    void addLoanTest() {
        String loanId = "loan123";
        reservation.addLoan(loanId);
        assertTrue(reservation.getLoans().contains(loanId), "The loan ID should be added to the reservation");
    }

    // Test to verify adding a recreational element to the reservation
    @Test
    void addRecreationalElementTest() {
        String elementId = "element123";
        reservation.addRecreationalElement(elementId);
        assertTrue(reservation.getRecreationalElements().contains(elementId), "The recreational element should be added to the reservation");
    }

    // Test to verify the state of elements (e.g., returned)
    @Test
    void testMarkElementsAsReturned() {
        reservation.markElementsAsReturned();
        assertTrue(reservation.isElementsReturned(), "The elementsReturned flag should be true after marking elements as returned");
    }

    // Test for adding a loan and getting the list of loans
    @Test
    void getLoansTest() {
        String loanId = "loan123";
        reservation.addLoan(loanId);
        assertEquals(1, reservation.getLoans().size(), "The reservation should contain one loan");
    }
}

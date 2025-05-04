package eci.cvds.mod2.modules;

import eci.cvds.mod2.util.State;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LoanTest {

    private Loan loan;

    // Setup method to initialize the Loan object before each test
    @BeforeEach
    void setUp() {
        loan = new Loan();  // Create a new Loan object before each test
    }

    // Teardown method to clean up after each test
    @AfterEach
    void tearDown() {
        // No resources to clean up for now
    }

    // Test to ensure that the Loan object is initialized correctly
    @Test
    void testLoanInitialization() {
        assertNotNull(loan, "The loan object should be initialized");
    }

    // Test to verify the setter and getter for the elementId
    @Test
    void testElementIdSetterGetter() {
        loan.setElementId("element123");
        assertEquals("element123", loan.getElementId(), "The elementId should be set and retrieved correctly");
    }

    // Test to verify the setter and getter for the state using valid enum value
    @Test
    void testStateSetterGetter() {
        loan.setState(State.PRESTAMO_PENDIENTE);
        assertEquals(State.PRESTAMO_PENDIENTE, loan.getState(), "The state should be set and retrieved correctly");
    }
}

package eci.cvds.mod2.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RecreationalElementTest {

    private RecreationalElement element;

    // Setup method to initialize the RecreationalElement object before each test
    @BeforeEach
    void setUp() {
        element = new RecreationalElement();  // Create a new RecreationalElement object
    }

    // Teardown method to clean up after each test (if necessary)
    @AfterEach
    void tearDown() {
        // Clean up resources if necessary
    }

    // Test to ensure that the RecreationalElement object is initialized correctly
    @Test
    void testElementInitialization() {
        assertNotNull(element, "The element object should be initialized");
    }

    // Test to verify the setter for the name
    @Test
    void testNameSetter() {
        element.setName("Soccer Ball");
        assertEquals("Soccer Ball", element.getName(), "The name should be set correctly");
    }

    // Test to verify the setter for quantity
    @Test
    void testQuantitySetter() {
        element.setQuantity(10);
        assertEquals(10, element.getQuantity(), "The quantity should be set correctly");
    }
}

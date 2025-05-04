package eci.cvds.mod2.modules;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class RoomTest {

    private Room room;

    // Setup method to initialize the Room object before each test
    @BeforeEach
    void setUp() {
        room = new Room("room1", "Building A", "Building A", 30, 5);  // Corrected constructor usage
    }

    // Teardown method to clean up after each test (if necessary)
    @AfterEach
    void tearDown() {
        // Clean up resources if necessary
    }

    // Test to ensure that the Room object is initialized correctly
    @Test
    void testRoomInitialization() {
        assertNotNull(room, "The room object should be initialized");
    }

    // Test to verify the getter for room ID
    @Test
    void testRoomIdGetter() {
        assertEquals("room1", room.getRoomId(), "The room ID should be 'room1'");
    }

    // Test to verify the getter for location
    @Test
    void testLocationGetter() {
        assertEquals("Building A", room.getLocation(), "The location should be 'Building A'");
    }

    // Test to verify the getter for capacity
    @Test
    void testCapacityGetter() {
        assertEquals(30, room.getCapacity(), "The room capacity should be 30");
    }

    // Test to verify the setter for currentPeople
    @Test
    void testCurrentPeopleSetter() {
        room.setCurrentPeople(10);
        assertEquals(10, room.getCurrentPeople(), "The current number of people should be updated to 10");
    }
}

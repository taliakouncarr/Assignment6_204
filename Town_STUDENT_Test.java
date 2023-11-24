import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Town_STUDENT_Test {

    private Town town1;
    private Town town2;
    private Town town3;

    @BeforeEach
    void setUp() throws Exception {
        town1 = new Town("City1");
        town2 = new Town("City2");
        town3 = new Town("City3");
    }

    @AfterEach
    void tearDown() throws Exception {
        town1 = town2 = town3 = null;
    }

    @Test
    public void testGetName() {
        assertEquals("City1", town1.getName());
        assertEquals("City3", town3.getName());
    }

    @Test
    public void testEquals() {
        Town town4 = new Town("City1");
        Town town5 = new Town("City4");
        assertTrue(town1.equals(town4));
        assertFalse(town2.equals(town5));
    }

    @Test
    public void testCompareTo() {
        Town town4 = new Town("City1");
        Town town5 = new Town("City4");
        assertTrue(town1.compareTo(town4) == 0);
        assertFalse(town2.compareTo(town1) < 1);
        assertTrue(town5.compareTo(town2) > 1);
    }

    @Test
    public void testHashCode() {
        Town town4 = new Town("City1");
        Town town5 = new Town("City4");
        assertEquals(town1.hashCode(), town4.hashCode());
        assertNotEquals(town3.hashCode(), town5.hashCode());
    }

    @Test
    public void testToString() {
        Town town5 = new Town("City4");
        assertEquals("City2", town2.toString());
        assertEquals("City4", town5.toString());
    }
}
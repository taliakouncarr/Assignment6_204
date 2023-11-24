import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

class Road_STUDENT_Test {
    private Town town1;
    private Town town2;
    private Town town3;

    private Road road1;
    private Road road2;
    private Road road3;

    @BeforeEach
    void setUp() throws Exception {
        town1 = new Town("CityA");
        town2 = new Town("CityB");
        town3 = new Town("CityC");

        road1 = new Road(town1, town2, 5, "RoadX");
        road2 = new Road(town2, town3, 8, "RoadY");
        road3 = new Road(town1, town3, 4, "RoadZ");

    }

    @AfterEach
    void tearDown() throws Exception {
        town1 = town2 = town3 = null;
        road1 = road2 = road3 = null;
    }

    @Test
    public void testGetName() {
        assertEquals("CityA", town1.getName());
        assertEquals("RoadX", road1.getName());
        assertEquals("CityC", town3.getName());
        assertEquals("RoadZ", road3.getName());
    }

    @Test
    public void testGetWeight() {
        assertEquals(5, road1.getWeight());
        assertEquals(4, road3.getWeight());
    }

    @Test
    public void testGetSource() {
        assertTrue(road1.getSource().getName().equals("CityA"));
        assertTrue(road2.getSource().getName().equals("CityB"));
        assertTrue(road3.getSource().getName().equals("CityA"));
    }

    @Test
    public void testGetDestination() {
        assertTrue(road1.getDestination().getName().equals("CityB"));
        assertTrue(road2.getDestination().getName().equals("CityC"));
        assertTrue(road3.getDestination().getName().equals("CityC"));
    }

    @Test
    public void testCompareTo() {
        Road road4 = new Road(town1, town2, 5, "RoadX");
        assertTrue(road1.compareTo(road4) == 0);
        assertTrue(road2.compareTo(road3) < 1);
        assertTrue(road3.compareTo(road4) > 1);
    }

    @Test
    public void testEquals() {
        Road road4 = new Road(town1, town2, 5, "RoadX");
        Road road5 = new Road(town2, town3, 5, "Road1");
        assertTrue(road4.equals(road1));
        assertFalse(road1.equals(road5));
        assertFalse(road2.equals(road3));
    }

    @Test
    public void testContains() {
        Road road4 = new Road(town1, town2, 5, "RoadX");
        assertTrue(road4.contains(town1));
        assertTrue(road1.contains(town2));
        assertTrue(road3.contains(town3));
        assertFalse(road1.contains(town3));
    }

    @Test
    public void testToString() {
        assertEquals("Road: RoadX, connecting CityA to CityB by 5 mi", road1.toString());
        assertEquals("Road: RoadY, connecting CityB to CityC by 8 mi", road2.toString());
    }

}

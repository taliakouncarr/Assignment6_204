import static org.junit.Assert.*;

import java.util.ArrayList;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class TownGraphManager_STUDENT_Test {
    private TownGraphManagerInterface graph;
    private String[] town_student;

    @Before
    public void setUp() throws Exception {
        graph = new TownGraphManager();
        town_student = new String[10];

        for (int i = 1; i < 10; i++) {
            town_student[i] = "City_" + i;
            graph.addTown(town_student[i]);
        }

        graph.addRoad(town_student[2], town_student[4], 4, "Road_X");
        graph.addRoad(town_student[1], town_student[3], 3, "Road_Y");
        graph.addRoad(town_student[4], town_student[5], 6, "Road_Z");
        graph.addRoad(town_student[2], town_student[8], 2, "Road_W");
        graph.addRoad(town_student[5], town_student[9], 6, "Road_V");
        graph.addRoad(town_student[2], town_student[6], 1, "Road_U");
        graph.addRoad(town_student[5], town_student[7], 2, "Road_T");
        graph.addRoad(town_student[8], town_student[5], 7, "Road_S");
        graph.addRoad(town_student[7], town_student[4], 1, "Road_R");
        graph.addRoad(town_student[3], town_student[7], 4, "Road_Q");

    }

    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testAddRoad() {
        ArrayList<String> roads = graph.allRoads();
        assertEquals("Road_Q", roads.get(0));
        assertEquals("Road_R", roads.get(1));
        assertEquals("Road_S", roads.get(2));
        assertEquals("Road_T", roads.get(3));
        graph.addRoad(town_student[2], town_student[9], 1, "Road_K");
        roads = graph.allRoads();
        assertEquals("Road_K", roads.get(0));
        assertEquals("Road_Q", roads.get(1));
        assertEquals("Road_R", roads.get(2));
        assertEquals("Road_T", roads.get(4));
        assertEquals("Road_Z", roads.get(10));

    }

    @Test
    public void testGetRoad() {
        assertEquals("Road_T", graph.getRoad(town_student[5], town_student[7]));
        assertEquals("Road_Q", graph.getRoad(town_student[3], town_student[7]));
    }

    @Test
    public void testAddTown() {
        assertEquals(false, graph.containsTown("City_P"));
        graph.addTown("City_P");
        assertEquals(true, graph.containsTown("City_P"));
        assertEquals(false, graph.containsTown("City_Q"));
        graph.addTown("City_Q");
        assertEquals(true, graph.containsTown("City_Q"));
    }

    @Test
    public void testDisjointGraph() {
        assertEquals(false, graph.containsTown("City_K"));
        graph.addTown("City_K");
        ArrayList<String> path = graph.getPath(town_student[2], "City_K");
        assertFalse(path.size() > 0);
    }

    @Test
    public void testContainsTown() {
        assertEquals(true, graph.containsTown("City_6"));
        assertEquals(false, graph.containsTown("City_16"));
        assertEquals(true, graph.containsTown("City_2"));
        assertEquals(false, graph.containsTown("City_77"));
    }

    @Test
    public void testContainsRoadConnection() {
        assertEquals(true, graph.containsRoadConnection(town_student[1], town_student[3]));
        assertEquals(false, graph.containsRoadConnection(town_student[3], town_student[5]));
        assertEquals(true, graph.containsRoadConnection(town_student[7], town_student[4]));
        assertEquals(false, graph.containsRoadConnection(town_student[4], town_student[9]));
    }

    @Test
    public void testAllRoads() {
        ArrayList<String> roads = graph.allRoads();
        assertEquals("Road_Q", roads.get(0));
        assertEquals("Road_R", roads.get(1));
        assertEquals("Road_S", roads.get(2));
        assertEquals("Road_Y", roads.get(8));
        assertEquals("Road_Z", roads.get(9));
    }

    @Test
    public void testDeleteRoadConnection() {
        assertEquals(true, graph.containsRoadConnection(town_student[2], town_student[4]));
        graph.deleteRoadConnection(town_student[2], town_student[4], "Road_X");
        assertEquals(false, graph.containsRoadConnection(town_student[2], town_student[4]));
    }

    @Test
    public void testDeleteTown() {
        assertEquals(true, graph.containsTown("City_2"));
        graph.deleteTown(town_student[2]);
        assertEquals(false, graph.containsTown("City_2"));
    }

    @Test
    public void testAllTowns() {
        ArrayList<String> roads = graph.allTowns();
        assertEquals("City_1", roads.get(0));
        assertEquals("City_2", roads.get(1));
        assertEquals("City_3", roads.get(2));
        assertEquals("City_4", roads.get(3));
        assertEquals("City_6", roads.get(5));
        assertEquals("City_7", roads.get(6));
    }

    @Test
    public void testGetPath() {
        ArrayList<String> path = graph.getPath(town_student[1], town_student[9]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_1 via Road_Y to City_3 3 mi", path.get(0).trim());
        assertEquals("City_3 via Road_Q to City_7 4 mi", path.get(1).trim());

    }

    @Test
    public void testGetPathA() {
        ArrayList<String> path = graph.getPath(town_student[1], town_student[7]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_1 via Road_Y to City_3 3 mi", path.get(0).trim());
        assertEquals("City_3 via Road_Q to City_7 4 mi", path.get(1).trim());
    }

    @Test
    public void testGetPathB() {
        ArrayList<String> path = graph.getPath(town_student[3], town_student[9]);
        assertNotNull(path);
        assertTrue(path.size() > 0);
        assertEquals("City_3 via Road_Q to City_7 4 mi", path.get(0).trim());
        assertEquals("City_7 via Road_T to City_5 2 mi", path.get(1).trim());
        assertEquals("City_5 via Road_V to City_9 6 mi", path.get(2).trim());
    }
}

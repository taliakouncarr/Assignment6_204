import static org.junit.Assert.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Iterator;
import java.util.Set;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Graph_STUDENT_Test {
    private GraphInterface<Town, Road> graph;
    private Town[] town_student;

    @Before
    public void setUp() throws Exception {
        graph = new Graph();
        town_student = new Town[10];

        for (int i = 1; i < 10; i++) {
            town_student[i] = new Town("City_" + i); // Changed town names
            graph.addVertex(town_student[i]);
        }

        graph.addEdge(town_student[2], town_student[4], 5, "Street_A"); // Changed road names and weight
        graph.addEdge(town_student[1], town_student[3], 2, "Street_B");
        graph.addEdge(town_student[4], town_student[5], 8, "Street_C");
        graph.addEdge(town_student[2], town_student[8], 3, "Street_D");
        graph.addEdge(town_student[5], town_student[9], 7, "Street_E");
        graph.addEdge(town_student[2], town_student[6], 1, "Street_F");
        graph.addEdge(town_student[5], town_student[7], 2, "Street_G");
        graph.addEdge(town_student[8], town_student[5], 6, "Street_H");
        graph.addEdge(town_student[7], town_student[4], 1, "Street_I");
        graph.addEdge(town_student[3], town_student[7], 4, "Street_J");
    }

    @After
    public void tearDown() throws Exception {
        graph = null;
    }

    @Test
    public void testGetEdge() {
        assertEquals(new Road(town_student[2], town_student[4], 5, "Street_A"), graph.getEdge(town_student[2], town_student[4]));
        assertEquals(new Road(town_student[3], town_student[7], 2, "Street_G"), graph.getEdge(town_student[3], town_student[7]));
    }

    @Test
    public void testAddEdge() {
        assertEquals(false, graph.containsEdge(town_student[2], town_student[9]));
        graph.addEdge(town_student[2], town_student[9], 8, "Street_D");
        assertEquals(true, graph.containsEdge(town_student[2], town_student[9]));
    }

    @Test
    public void testAddVertex() {
        Town newTown = new Town("City_K"); // Changed town name
        assertEquals(false, graph.containsVertex(newTown));
        graph.addVertex(newTown);
        assertEquals(true, graph.containsVertex(newTown));
    }

    @Test
    public void testContainsEdge() {
        assertEquals(true, graph.containsEdge(town_student[5], town_student[5]));
        assertEquals(false, graph.containsEdge(town_student[2], town_student[9]));
        assertEquals(true, graph.containsEdge(town_student[8], town_student[5]));
        assertEquals(false, graph.containsEdge(town_student[1], town_student[7]));
    }

    @Test
    public void testContainsVertex() {
        assertEquals(true, graph.containsVertex(new Town("City_5")));
        assertEquals(false, graph.containsVertex(new Town("City_15")));
        assertEquals(false, graph.containsVertex(new Town("City_50")));
        assertEquals(true, graph.containsVertex(new Town("City_1")));
    }

    @Test
    public void testEdgeSet() {
        Set<Road> roads = graph.edgeSet();
        ArrayList<String> roadArrayList = new ArrayList<String>();
        for (Road road : roads)
            roadArrayList.add(road.getName());
        Collections.sort(roadArrayList);
        assertEquals("Street_A", roadArrayList.get(0));
        assertEquals("Street_B", roadArrayList.get(1));
        assertEquals("Street_C", roadArrayList.get(2));
        assertEquals("Street_D", roadArrayList.get(3));
        assertEquals("Street_E", roadArrayList.get(4));
        assertEquals("Street_G", roadArrayList.get(6));
        assertEquals("Street_J", roadArrayList.get(9));
    }

    @Test
    public void testEdgesOf() {
        Set<Road> std_roads = graph.edgesOf(town_student[2]);
        ArrayList<String> std_roadArrayList = new ArrayList<String>();
        for (Road road : std_roads)
            std_roadArrayList.add(road.getName());
        Collections.sort(std_roadArrayList);
        assertEquals("Street_A", std_roadArrayList.get(0));
        assertEquals("Street_D", std_roadArrayList.get(1));
        assertEquals("Street_F", std_roadArrayList.get(2));
    }

    @Test
    public void testRemoveEdge() {
        assertEquals(true, graph.containsEdge(town_student[1], town_student[3]));
        graph.removeEdge(town_student[4], town_student[5], 8, "Street_C");
        assertEquals(false, graph.containsEdge(town_student[4], town_student[5]));
    }

    @Test
    public void testRemoveVertex() {
        assertEquals(true, graph.containsVertex(town_student[2]));
        graph.removeVertex(town_student[2]);
        assertEquals(false, graph.containsVertex(town_student[2]));
    }

    @Test
    public void testVertexSet() {
        Set<Town> roads = graph.vertexSet();
        assertEquals(true, roads.contains(town_student[1]));
        assertEquals(true, roads.contains(town_student[9]));
        assertEquals(true, roads.contains(town_student[7]));
        assertEquals(true, roads.contains(town_student[2]));
        assertEquals(true, roads.contains(town_student[3]));
    }

    @Test
    public void testTown_2ToTown_9() {
        String beginTown = "City_2", endTown = "City_9";
        Town beginIndex = null, endIndex = null;
        Set<Town> towns = graph.vertexSet();
        Iterator<Town> iterator = towns.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {

            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);

            assertEquals("City_2 via Street_A to City_4 5 mi", path.get(0).trim());
            assertEquals("City_4 via Street_I to City_7 1 mi", path.get(1).trim());
        } else
            fail("Town names are not valid");

    }

    @Test
    public void testTown1ToTown_9() {
        String beginTown = "City_1", endTown = "City_9";
        Town beginIndex = null, endIndex = null;
        Set<Town> towns = graph.vertexSet();
        Iterator<Town> iterator = towns.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {

            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);

            assertEquals("City_1 via Street_B to City_3 2 mi", path.get(0).trim());
            assertEquals("City_3 via Street_J to City_7 4 mi", path.get(1).trim());
            assertEquals("City_7 via Street_G to City_5 2 mi", path.get(2).trim());
        } else
            fail("Town names are not valid");
    }

    @Test
    public void testTown_5ToTown_9() {
        String beginTown = "City_5", endTown = "City_9";
        Town beginIndex = null, endIndex = null;
        Set<Town> towns = graph.vertexSet();
        Iterator<Town> iterator = towns.iterator();
        while (iterator.hasNext()) {
            Town town = iterator.next();
            if (town.getName().equals(beginTown))
                beginIndex = town;
            if (town.getName().equals(endTown))
                endIndex = town;
        }
        if (beginIndex != null && endIndex != null) {

            ArrayList<String> path = graph.shortestPath(beginIndex, endIndex);
            assertNotNull(path);
            assertTrue(path.size() > 0);

            assertEquals("City_5 via Street_E to City_9 7 mi", path.get(0).trim());
            // Add more assertions if needed
        } else
            fail("Town names are not valid");
    }
}

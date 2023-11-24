import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;

/**
 * @author Talia
 */
public class TownGraphManager implements TownGraphManagerInterface {

	Graph graph = new Graph();

	/**
	 * Adds a road with 2 towns and a road name
	 * 
	 * @param town1    name of town 1 (lastname, firstname)
	 * @param town2    name of town 2 (lastname, firstname)
	 * @param roadName name of road
	 * @return true if the road was added successfully
	 */
	@Override
	public boolean addRoad(String town1, String town2, int weight, String roadName) {
		Town newTown1 = new Town(town1);
		Town newTown2 = new Town(town2);
		Road addNewRoad;

		graph.addVertex(newTown1);
		graph.addVertex(newTown2);

		addNewRoad = graph.addEdge(newTown1, newTown2, weight, roadName);

		if (addNewRoad != null) {

			return true;
		}
		return false;
	}

	/**
	 * Returns the name of the road that both towns are connected through
	 * 
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return name of road if town 1 and town2 are in the same road, returns null
	 *         if not
	 */
	@Override
	public String getRoad(String town1, String town2) {
		Road road = graph.getEdge(new Town(town1), new Town(town2));
		if (road != null) {
			return road.getName();

		}
		return null;
	}

	/**
	 * Adds a town to the graph
	 * 
	 * @param v the town's name (lastname, firstname)
	 * @return true if the town was successfully added, false if not
	 */
	@Override
	public boolean addTown(String v) {
		return graph.addVertex(new Town(v));
	}

	/**
	 * Gets a town with a given name
	 * 
	 * @param name the town's name
	 * @return the Town specified by the name, or null if town does not exist
	 */
	@Override
	public Town getTown(String name) {
		for (Town town : graph.vertexSet()) {
			if (town.getName().equals(name)) {
				return town;
			}
		}
		return null;
	}

	/**
	 * Determines if a town is already in the graph
	 * 
	 * @param v the town's name
	 * @return true if the town is in the graph, false if not
	 */
	@Override
	public boolean containsTown(String v) {
		return graph.containsVertex(new Town(v));
	}

	/**
	 * Determines if a road is in the graph
	 * 
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return true if the road is in the graph, false if not
	 */
	@Override
	public boolean containsRoadConnection(String town1, String town2) {
		return graph.containsEdge(new Town(town1), new Town(town2));
	}

	/**
	 * Creates an arraylist of all road titles in sorted order by road name
	 * 
	 * @return an arraylist of all road titles in sorted order by road name
	 */
	@Override
	public ArrayList<String> allRoads() {
		ArrayList<Road> road = new ArrayList<Road>(graph.edgeSet());
		ArrayList<String> s = new ArrayList<>();

		for (Road roads : road) {
			s.add(roads.getName());
		}
		Collections.sort(s);
		return s;
	}

	/**
	 * Deletes a road from the graph
	 * 
	 * @param town1    name of town 1 (lastname, firstname)
	 * @param town2    name of town 2 (lastname, firstname)
	 * @param roadName the road name
	 * @return true if the road was successfully deleted, false if not
	 */
	@Override
	public boolean deleteRoadConnection(String town1, String town2, String road) {
		Road road1 = graph.getEdge(new Town(town1), new Town(town2));
		Road road2 = graph.removeEdge(new Town(town1), new Town(town2), road1.getWeight(), road);
		if (road2 != null) {
			return true;
		}

		return false;
	}

	/**
	 * Deletes a town from the graph
	 * 
	 * @param v name of town (lastname, firstname)
	 * @return true if the town was successfully deleted, false if not
	 */
	@Override
	public boolean deleteTown(String v) {
		return graph.removeVertex(new Town(v));
	}

	/**
	 * Creates an arraylist of all towns in alphabetical order (last name, first
	 * name)
	 * 
	 * @return an arraylist of all towns in alphabetical order (last name, first
	 *         name)
	 */
	@Override
	public ArrayList<String> allTowns() {
		ArrayList<Town> moreTown = new ArrayList<Town>(graph.vertexSet());
		ArrayList<String> s = new ArrayList<String>();
		for (Town town : moreTown) {
			s.add(town.getName());
		}
		Collections.sort(s);
		return s;

	}

	/**
	 * Returns the shortest path from town 1 to town 2
	 * 
	 * @param town1 name of town 1 (lastname, firstname)
	 * @param town2 name of town 2 (lastname, firstname)
	 * @return an Arraylist of roads connecting the two towns together, null if the
	 *         towns have no path to connect them.
	 */
	@Override
	public ArrayList<String> getPath(String town1, String town2) {
		return graph.shortestPath(new Town(town1), new Town(town2));
	}

	public void populateTownGraph(File selectedFile) throws FileNotFoundException {
		try (Scanner fileScanner = new Scanner(selectedFile)) {
			while (fileScanner.hasNextLine()) {
				String[] str = fileScanner.nextLine().split(",|;");
				graph.addVertex(new Town(str[2]));
				graph.addVertex(new Town(str[3]));
				graph.addEdge(new Town(str[2]), new Town(str[3]), Integer.parseInt(str[1]), str[0]);
			}
		} catch (FileNotFoundException e) {
			// Handle the exception or rethrow it if needed
			throw e;
		}
	}

}
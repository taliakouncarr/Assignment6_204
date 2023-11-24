import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

public class Graph implements GraphInterface<Town, Road> {

	/**
	 * @author Talia
	 */

	// variables
	private HashSet<Town> town;
	private HashSet<Road> road;
	private HashMap<Town, Integer> distance;
	private HashMap<Town, Town> before;

	/**
	 * Constructor Initializes variables
	 */
	public Graph() {
		town = new HashSet<Town>();
		road = new HashSet<Road>();
		before = new HashMap<Town, Town>();
		distance = new HashMap<Town, Integer>();

	}

	/**
	 * Returns the edge between two towns (sourceVertex and destiVertex) if it
	 * exists in the road set; otherwise, returns null.
	 * 
	 * @param sourceVertex
	 * @param destiVertex
	 */
	@Override
	public Road getEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null) {
			return null;
		}

		for (Road roads : road) {
			if (roads.contains(destinationVertex) && roads.contains(sourceVertex)) {
				return roads;
			}
		}

		return null;
	}

	/**
	 * Adds a new road to the graph with the specified details (source, destination,
	 * weight, description).
	 * 
	 * @param sourceVertex
	 * @param destiVertex
	 * @param weight
	 * @param description
	 * 
	 * @throws NullPointerException
	 * @throws IllegalArgumentExcepttion
	 */
	@Override
	public Road addEdge(Town sourceVertex, Town destinationVertex, int weight, String description)
			throws NullPointerException, IllegalArgumentException {
		if (sourceVertex == null || destinationVertex == null) {
			throw new NullPointerException();
		}

		if (!(town.contains(sourceVertex) || town.contains(destinationVertex))) {
			throw new IllegalArgumentException();
		}

		Road newRoad = new Road(sourceVertex, destinationVertex, weight, description);

		try {
			road.add(newRoad);
			return newRoad;
		} catch (Exception e) {
			return null;
		}
	}

	/**
	 * Adds a new town to the graph if it doesn't already exist.
	 * 
	 * @throws NullPointerException
	 * @param anotherTown
	 */
	@Override
	public boolean addVertex(Town anotherTown) throws NullPointerException {
		if (anotherTown == null) {
			throw new NullPointerException();
		}
		for (Town T : town) {
			if (T.equals(anotherTown)) {
				return false;
			}
		}
		town.add(anotherTown);
		return true;
	}

	/**
	 * Checks if an edge between two towns (sourceVertex and destiVertex) exists in
	 * the graph.
	 * 
	 * @param sourceVertex
	 * @param destiVertex
	 * @return true if edge exists, false if otherwise
	 */
	@Override
	public boolean containsEdge(Town sourceVertex, Town destinationVertex) {
		if (sourceVertex == null || destinationVertex == null
				|| !(town.contains(sourceVertex) || town.contains(destinationVertex))) {
			return false;
		}

		for (Road anotherRoad : road) {
			if (anotherRoad.contains(destinationVertex) && anotherRoad.contains(sourceVertex)) {
				return true;
			}
		}

		return false;
	}

	/**
	 * Checks if a town exists in the graph.
	 * 
	 * @param anotherTown
	 * @return true if town exists
	 */
	@Override
	public boolean containsVertex(Town anotherTown) {
		for (Town t : town) {
			if (t.equals(anotherTown)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns a set of all roads in the graph.
	 * 
	 */
	@Override
	public Set<Road> edgeSet() {
		HashSet<Road> newRoad = new HashSet<Road>();

		for (Road roads : road) {
			newRoad.add(roads);
		}

		return newRoad;
	}

	/**
	 * @param vertex Returns a set of roads connected to a specific town (vertex).
	 */
	@Override
	public Set<Road> edgesOf(Town vertex) throws IllegalArgumentException, NullPointerException {
		if (vertex == null) {
			throw new NullPointerException();
		}

		HashSet<Road> road1 = new HashSet<Road>();

		for (Road roads : road) {
			if (roads.contains(vertex)) {
				road1.add(roads);
			}
		}

		return road1;
	}

	/**
	 * Removes a road from the graph based on the specified details (source,
	 * destination, weight, description).
	 * 
	 * @param sourceVertex
	 * @param destiVertex
	 * @param weight
	 * @param description
	 * @return Returns the removed road, or null if no matching road is found.
	 */
	@Override
	public Road removeEdge(Town sourceVertex, Town destinationVertex, int weight, String description) {
		Road removeRoad = null;

		Iterator<Road> iterator = road.iterator();
		while (iterator.hasNext()) {
			Road roads = iterator.next();
			if (roads.contains(sourceVertex) && roads.contains(destinationVertex) && roads.getWeight() == weight
					&& roads.getName().equals(description)) {
				iterator.remove();
				removeRoad = roads;
				break;
			}
		}

		return removeRoad;
	}

	/**
	 * Removes a town from the graph.
	 * 
	 * @param anotherTown
	 * @return Returns true if the town is successfully removed, otherwise false.
	 */
	@Override
	public boolean removeVertex(Town anotherTown) {

		return town.remove(anotherTown);

	}

	/**
	 * Returns a set of all towns in the graph.
	 */
	@Override
	public Set<Town> vertexSet() {
		return town;
	}

	/**
	 * Finds the shortest path between two towns (sourceVertex and destiVertex)
	 * using Dijkstra's algorithm. Uses a helper method findConnectingRoad to find
	 * the road connecting two towns.
	 * 
	 * @param sourceVertex
	 * @param destiVertex
	 * @return Uses a helper method findConnectingRoad to find the road connecting
	 *         two towns.
	 */
	@Override
	public ArrayList<String> shortestPath(Town sourceVertex, Town destinationVertex) {
		ArrayList<String> shortestPath = new ArrayList<String>();

		dijkstraShortestPath(sourceVertex);

		while (!sourceVertex.equals(destinationVertex) && before.containsKey(destinationVertex)) {
			Road connectingRoad = findConnectingRoad(destinationVertex, before.get(destinationVertex));
			if (connectingRoad != null) {
				shortestPath.add(0, before.get(destinationVertex).getName() + " via " + connectingRoad.getName() + " to "
						+ destinationVertex.getName() + " " + connectingRoad.getWeight() + " mi");
			}
			destinationVertex = before.get(destinationVertex);
		}

		return shortestPath;
	}

	/**
	 * Helper method to find the road connecting two towns (town1 and town2) in the
	 * graph.
	 * 
	 * @param town1
	 * @param town2
	 * @return
	 */
	private Road findConnectingRoad(Town town1, Town town2) {
		for (Road roadIterator : road) {
			if (roadIterator.contains(town1) && roadIterator.contains(town2)) {
				return roadIterator;
			}
		}
		return null;
	}

	/**
	 * Helper method to perform Dijkstra's algorithm to find the shortest paths from
	 * a source town to all other towns. Updates the before and distance maps.
	 * 
	 * @param sourceVertex
	 */
	@Override
	public void dijkstraShortestPath(Town sourceVertex) {
		Set<Town> remainingTowns = new HashSet<>(town);

		for (Town townVertex : town) {
			distance.put(townVertex, Integer.MAX_VALUE);
		}
		distance.put(sourceVertex, 0);

		while (!remainingTowns.isEmpty()) {
			Town currentVertex = findMinDistanceVertex(remainingTowns);

			if (currentVertex == null) {
				break;
			}

			for (Road roadNode : road) {
				if (roadNode.contains(currentVertex)) {
					Town neighborVertex = (roadNode.getSource().equals(currentVertex)) ? roadNode.getDestination()
							: roadNode.getSource();

					if (remainingTowns.contains(neighborVertex)) {
						int newDistance = distance.get(currentVertex) + roadNode.getWeight();

						if (newDistance < distance.get(neighborVertex)) {
							before.put(neighborVertex, currentVertex);
							distance.put(neighborVertex, newDistance);
						}
					}
				}
			}

			remainingTowns.remove(currentVertex);
		}
	}

	/**
	 * Helper method to find the town with the minimum distance value among the
	 * remaining towns during Dijkstra's algorithm.
	 * 
	 * @param remainingTowns
	 * @return
	 */
	private Town findMinDistanceVertex(Set<Town> remainingTowns) {
		int minDistance = Integer.MAX_VALUE;
		Town minVertex = null;

		for (Town townVertex : remainingTowns) {
			int vertexDistance = distance.get(townVertex);

			if (vertexDistance < minDistance) {
				minDistance = vertexDistance;
				minVertex = townVertex;
			}
		}

		return minVertex;
	}

}
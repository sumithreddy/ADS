/*
 * This class is used to hold the ajacency details for each node for each path
 * This class is used inside Node.java
 */

public class AdjacencyDetails {
	int destination;
	int weight;
	
	public AdjacencyDetails(int destination, int weight){
		this.destination =  destination;
		this.weight = weight;
	}
	public int getDestination() {
		return destination;
	}

	public void setDestination(int destination) {
		this.destination = destination;
	}

	public int getWeight() {
		return weight;
	}

	public void setWeight(int weight) {
		this.weight = weight;
	}

}

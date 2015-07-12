/*
 * This class maintains the basic structure as in the file for part 1
 */

import java.util.ArrayList;

public class Node {
	int nodeId;
	int nodeValue;
	ArrayList<AdjacencyDetails> adjacencyList = new ArrayList<AdjacencyDetails>();
	
	public int getNodeId() {
		return nodeId;
	}
	public void setNodeId(int nodeId) {
		this.nodeId = nodeId;
	}
	public int getNodeValue() {
		return nodeValue;
	}
	public void setNodeValue(int nodeValue) {
		this.nodeValue = nodeValue;
	}
	public ArrayList<AdjacencyDetails> getAdjacencyList() {
		return adjacencyList;
	}
	public void setAdjacencyList(ArrayList<AdjacencyDetails> adjacencyList) {
		this.adjacencyList = adjacencyList;
	}
	
	public Node(int nodeId){
		this.nodeId = nodeId;
		this.nodeValue = Integer.MAX_VALUE;
	}
	public void addAdjacency(AdjacencyDetails n){
		adjacencyList.add(n);
	}
}

/*
 * This class contains the main class for the part 1
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class ssp {
	public static int totalCost;
	public static ArrayList<Integer> list ;
	public static fibonacciHeap fQueue;
	public static HashMap<Integer, Node> nodesMap;
	public static HashMap<Integer, fHeapNode> fNodeMap;
	
	public static void main(String[] args) throws FileNotFoundException {
		String filePath = args[0];
		int source_node = Integer.parseInt(args[1]);
		int destination_node = Integer.parseInt(args[2]);
		Dijkistra(filePath,source_node,destination_node);
		for(int k: list){
			System.out.print(k+" ");
		}
		System.out.println("");
		
	}
	/*
	 * Reads the file and creates Nodes and then calls the ComputeCost function
	 * to calculate the shortest path
	 */
	
	public static ArrayList<Integer> Dijkistra(String filePath, int source_node, int destination_node) throws FileNotFoundException{
		Scanner fileScanner = new Scanner(new File(filePath)); // Reading the file
		int no_nodes = fileScanner.nextInt();
		int no_edges = fileScanner.nextInt();
		nodesMap = new HashMap<Integer, Node>(); // creating nodes
		for (int i = 0; i < no_nodes; i++) {
			nodesMap.put(i, new Node(i));
		}
		int startNode, endNode, cost;
		Node n, start_node, end_node; // form the adjacency lists
		while (fileScanner.hasNext()) {
			startNode = fileScanner.nextInt();
			endNode = fileScanner.nextInt();
			cost = fileScanner.nextInt();
			n = nodesMap.get(startNode);
			n.addAdjacency(new AdjacencyDetails(endNode, cost));
			n = nodesMap.get(endNode);
			n.addAdjacency(new AdjacencyDetails(startNode, cost));
		}
		fileScanner.close();
		start_node = nodesMap.get(source_node);
		end_node = nodesMap.get(destination_node);

		totalCost = ComputeCost(start_node, end_node,no_nodes);
		System.out.println(totalCost);
		return list;
	}
	/*
	 * creates the fHeapNodes and runs procedure to compute shortest path
	 */
	public static int ComputeCost(Node start_node, Node end_node, int no_nodes) {
		Node n;
		fNodeMap = new HashMap<Integer, fHeapNode>(); // creating fNode
		start_node.setNodeValue(0);
		fQueue = new fibonacciHeap();
		for (int i = 0; i < no_nodes; i++) {
			n = nodesMap.get(i);
			fNodeMap.put(i, new fHeapNode(n));
			fQueue.Insert(fNodeMap.get(i));
		}
		list = new ArrayList<Integer>();
		HashMap<Integer,Integer> path = new HashMap<Integer,Integer>();
		int nodeID;
		while(true){
		fHeapNode min_node =  fQueue.extractMin();
		if(min_node.node.getNodeId() == end_node.nodeId){
			totalCost = min_node.node.getNodeValue();
			list.add(0,end_node.nodeId);
			while(true){
				if(min_node.node.nodeId == start_node.nodeId){
					break;
				}
				nodeID = path.get(min_node.node.nodeId);
				list.add(0,nodeID);
				min_node = fNodeMap.get(nodeID);
			}
			return totalCost;
		}
		
		for(AdjacencyDetails d : min_node.node.adjacencyList){
			Node destination = nodesMap.get(d.getDestination());
			if(destination.getNodeValue()> min_node.node.getNodeValue()+d.getWeight()){
				fHeapNode k = fNodeMap.get(destination.getNodeId());
				if(path.containsKey(k.node.nodeId)) {
					path.remove(k.node.nodeId);
				}
				path.put(k.node.nodeId, min_node.node.nodeId);
				fQueue.decreaseKey(k, min_node.node.getNodeValue()+d.getWeight());
			}
		}
		}
		
	}
}

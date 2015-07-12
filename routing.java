/*
 * This contains the main class for the second part of the project
 */

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class routing {

	public static void main(String[] args) throws FileNotFoundException {
		String filePath = args[0];
		String filePathIP = args[1];
		int source_node = Integer.parseInt(args[2]);
		int destination_node = Integer.parseInt(args[3]);
		// start part 2 routing scheme
		RoutingScheme(filePath, filePathIP, source_node, destination_node);

	}
	
	/*
	 * Computes all the dijkistra paths from each node in the shortest path
	 * Reads the IP mapping file
	 * Computes trie for each node in the shortest path
	 * prints the path as required by part2 of the project
	 * 
	 */
	private static void RoutingScheme(String filePath, String filePathIP,
			int source_node, int destination_node) throws FileNotFoundException {
		ssp dks = new ssp();
		// find shortest path
		ArrayList<Integer> path = dks.Dijkistra(filePath, source_node,destination_node);
		@SuppressWarnings("resource")
		Scanner fileScan = new Scanner(new File(filePathIP));
		HashMap<Integer, String> ipMap = new HashMap<Integer, String>();
		int no_nodes = 0;
		// reading the IP mapping file
		while (fileScan.hasNext()) {
			ipMap.put(no_nodes, convertIpToBinaryString(fileScan.next()));
			no_nodes++;
		}
		fileScan.close();
		// HashMap to hold trie
		HashMap<Integer, Trie> trieMap = new HashMap<Integer, Trie>();
		Node n;
		// Calculate all shortest paths and compute trie for each node.
		for (int k : path) {
			Trie trie = new Trie();
			for (int j = 0; j < no_nodes; j++) {
				if (k != j) {
					for (int start = 0; start < no_nodes; start++) {
						n = dks.nodesMap.get(start);
						n.setNodeValue(Integer.MAX_VALUE);
					}
					dks.ComputeCost(dks.nodesMap.get(k), dks.nodesMap.get(j),no_nodes);
					trie.insert(ipMap.get(j), dks.list.get(1));
				}
			}
			trieMap.put(k, trie);
			trie.root = trie.pruning(trie.root);
		}// end for
		
		StringBuffer sf = new StringBuffer();
		//Calculate the prefix of the matching IPs in the routing path
		for (int k : path) {
			if (k != destination_node) {
				Trie t = trieMap.get(k);
				//t.findPath(ipMap.get(destination_node));
				sf = sf.append(t.findPath(ipMap.get(destination_node)));
			}
			
		}
		System.out.println(sf.toString().substring(0,sf.length()-1));

	}
/*
 * To convert given IP TO 32 BITS
 */
	public static String convertIpToBinaryString(String ip) {
		String[] splitString = ip.split("\\.");
		StringBuffer res = new StringBuffer("");
		for (int i = 0; i < 4; i++) {
			int x = Integer.parseInt(splitString[i]);
			StringBuffer m = new StringBuffer(Integer.toBinaryString(x));
			StringBuffer l = new StringBuffer("");
			for (int j = 0; j < 8 - m.length(); j++) {
				l.append('0');
			}
			l.append(m);
			res.append(l);
		}
		return res.toString();
	}

}

/*
 * TrieNode is the node structure for Nodes in trie
 * isLeaf determines whether its a leaf node or a branch node
 */
public class TrieNode {

	boolean isLeaf;
	int splitIndex;
	TrieNode zero;
	TrieNode one;
	TrieNode parent;
	
	//leafNode
	String ipValue;
	int nextHop;
	
	public TrieNode(boolean isLeaf, int splitIndex, TrieNode zero, TrieNode one, TrieNode parent, String ipValue, int nextHop){
		this.isLeaf = isLeaf;
		this.splitIndex = splitIndex;
		this.zero = zero;
		this.one = one;
		this.parent = parent;
		this.ipValue = ipValue;
		this.nextHop = nextHop;
				
	}
}

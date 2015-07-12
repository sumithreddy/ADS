/*
 * Trie with all the functionalities required
 */
public class Trie {
	TrieNode root;
	public Trie(){
		root = null;
	}
	
	/*
	 * To insert TrieNodes into the Trie
	 */
	public void insert(String IP, int nextHop){
		TrieNode zero;
		TrieNode one;
		if(root == null){
			root = new TrieNode(true,-1, null, null, null, IP, nextHop);
		}else{
			if(root.isLeaf == true){
				int split = findSplitIndex(root.ipValue, IP);
				if(IP.charAt(split)== '0'){
					 zero = new TrieNode(true, -1, null, null, null, IP, nextHop);
					 one = root;
				}else{
					 one = new TrieNode(true, -1, null, null, null, IP, nextHop);
					 zero = root;
				}
				TrieNode nn = new TrieNode(false, split, zero, one, null, "", -1);
				one.parent = nn;
				zero.parent = nn;
				root = nn;
			}else{
				TrieNode node = root;
				//go till the leaf and find the split bit 
				while(!node.isLeaf){
					if(IP.charAt(node.splitIndex)== '1'){
						node = node.one;
					}else{
						node = node.zero;
					}
					
				}
				int split = findSplitIndex(node.ipValue, IP);
				//traverse up to insert to put the new node in the trie
				while(node != root && node.parent.splitIndex > split){
					node = node.parent;
				}
				if(node == root){
					if(IP.charAt(split)== '0'){
						 zero = new TrieNode(true, -1, null, null, null, IP, nextHop);
						 one = root;
					}else{
						 one = new TrieNode(true, -1, null, null, null, IP, nextHop);
						 zero = root;
					}
					TrieNode nn = new TrieNode(false, split, zero, one, null, "", -1);
					one.parent = nn;
					zero.parent = nn;
					root = nn;
				}else{
					if(IP.charAt(split)== '0'){
						 zero = new TrieNode(true, -1, null, null, null, IP, nextHop);
						 one = node;
					}else{
						 one = new TrieNode(true, -1, null, null, null, IP, nextHop);
						 zero = node;
					}
					TrieNode nn = new TrieNode(false, split, zero, one, node.parent, "", -1);
					one.parent = nn;
					zero.parent = nn;
					if(IP.charAt(nn.parent.splitIndex)== '1') nn.parent.one = nn;
					else nn.parent.zero = nn;
				}
				
			}
		}
		
	}
	/*
	 * To remove TrieNodes with same nextHop values and prune the trie
	 */
	public TrieNode pruning(TrieNode node){
		if(node == null || node.isLeaf )return node;
		TrieNode zero = pruning(node.zero);
		TrieNode one = pruning(node.one);
		if(zero == null && one == null) return zero;
		else if(one.isLeaf && zero.isLeaf && one.nextHop == zero.nextHop){
			TrieNode parent = node.parent;
			node = one;
			node.parent = parent;
			if(parent == null){
				root = node;
			}else{
				if(node.ipValue.charAt(parent.splitIndex)== '1') parent.one = node;
				else parent.zero = node;
			}
		}
		return node; 
	}
	/*
	 * To find the routing path in a single trie given the destination node.
	 */
	public String findPath(String destIP){
		TrieNode node = root;
		while(!node.isLeaf){
			if(destIP.charAt(node.splitIndex)== '1')
				node = node.one;
			else node = node.zero;
		}
		return (destIP.substring(0,node.parent.splitIndex+1)+ " ");
		
	}
	
	/*
	 * To find the splitIndex given two IP Values.
	 */
	public int findSplitIndex(String ipValue, String IP) {
		int i = 0;
		while(i<32){
			if(ipValue.charAt(i) != IP.charAt(i)){
				break;
			}
			i++;
		}
		return i;
	}
}

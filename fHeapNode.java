/*
 * This class contains the Node structure required for the fibonacciHeap
 * Node.java class is used here to get the details of the input file.
 */

public class fHeapNode {
	int degree ;
	Node node;
	fHeapNode child;
	fHeapNode parent;
	fHeapNode left,right;
	boolean childCut;
	
	public fHeapNode(Node n){
		this.node = n;
		this.degree = 0;
		this.childCut = false;
	}
}

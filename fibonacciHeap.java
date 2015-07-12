/*
 * This class Implements the functionalities of the fibonacciHeap
 */

import java.util.HashMap;
import java.util.LinkedList;
import java.util.Queue;

public class fibonacciHeap {
	fHeapNode root;

	/*
	 * To remove the min node from the heap
	 */
	public fHeapNode extractMin() {
		fHeapNode temp = root;
		if (root.right == root) {
			if (root.child != null) {
				root = root.child;
				root.parent = null;
				fHeapNode f = root.right;
				while (f != root) {
					f.parent = null;
					f = f.right;
				}
			} else {
				root = null; // no nodes left
			}
		} else {
			if (root.child != null) {

				root.child.parent = null;
				fHeapNode f = root.child.right;
				while (f != root.child) {
					f.parent = null;
					f = f.right;
				}

				fHeapNode left = root.left;
				fHeapNode right = root.right;
				left.right = root.child;
				right.left = root.child.left;
				root.child.left.right = right;
				root.child.left = left;
				root = root.child; // changed this from root.child
			} else {
				fHeapNode nMin = root.right;
				removeFromList(root);
				root = nMin;

			}
		}
		root = pairwiseCombine();
		return temp;
	}

	/*
	 * To do pairWise combine of the same degree structures in the top layer
	 */
	public fHeapNode pairwiseCombine() {
		if (root == null)
			return null;
		HashMap<Integer, fHeapNode> degreeMap = new HashMap<Integer, fHeapNode>();
		Queue<fHeapNode> queue = new LinkedList<fHeapNode>();
		fHeapNode current = root;
		queue.add(current);
		current = current.right;
		// adding all the top layer into a queue
		while (current != root) {
			queue.add(current);
			current = current.right;
		}
		fHeapNode newMin = root;
		while (!queue.isEmpty()) {
			current = queue.poll();

			while (degreeMap.containsKey(current.degree)) {
				fHeapNode m = degreeMap.remove(current.degree);
				if (current.node.nodeValue <= m.node.nodeValue) {
					removeFromList(m);
					fHeapNode q = m;
					m = current;
					current = q;
				} else {
					removeFromList(current);
				}
				m.degree++;
				if (m.child == null) {
					m.child = current;
					current.parent = m;
					current.childCut = false;
				} else {
					current.parent = m;
					current.right = m.child.right;
					current.left = m.child;
					m.child.right.left = current;
					m.child.right = current;
					current.childCut = false;
				}
				current = m;
			}
			if (current.node.nodeValue <= newMin.node.nodeValue)
				newMin = current;
			degreeMap.put(current.degree, current);
		}
		root = newMin;

		return root;
	}
	/*
	 * To remove node from a particular list and update the pointers
	 */
	public void removeFromList(fHeapNode rNode) {
		// removing next node from the top list
		rNode.left.right = rNode.right;
		rNode.right.left = rNode.left;
		rNode.left = rNode;
		rNode.right = rNode;

	}

	public fHeapNode decreaseKey(fHeapNode Node, int newValue) {
		if (Node.parent == null) {
			Node.node.setNodeValue(newValue);
			if (newValue < root.node.getNodeValue())
				root = Node;
		} else {
			if (Node.parent.node.getNodeValue() <= newValue) {
				Node.node.setNodeValue(newValue);
			} else {
				Node.parent.degree--;
				Node.node.setNodeValue(newValue);
				// changing the child of parent if needed
				if (Node.parent.child == Node) {
					if (Node.left == Node)
						Node.parent.child = null;
					else {
						Node.left.right = Node.right;
						Node.right.left = Node.left;
						Node.parent.child = Node.left;
					}
				} else {
					Node.left.right = Node.right;
					Node.right.left = Node.left;

				}
				// check Child Cut Values
				cascadeCut(Node.parent);
				Insert(Node);
			}
		}
		return root;
	}

	/*
	 * To Check and update the heap structure by using childcut values of parent nodes
	 * This is called in decreaseKey
	 */
	private void cascadeCut(fHeapNode parent) {
		while (parent.parent != null && parent.childCut == true) {
			parent.parent.degree--;
			// changing the child of parent if needed
			if (parent.parent.child == parent) {
				if (parent.left == parent)
					parent.parent.child = null;
				else {
					parent.left.right = parent.right;
					parent.right.left = parent.left;
					parent.parent.child = parent.left;
				}
			} else {
				parent.left.right = parent.right;
				parent.right.left = parent.left;
			}
			fHeapNode n = parent.parent;
			Insert(parent);
			parent = n;
		}
		if (parent.parent != null)
			parent.childCut = true;
	}

	// Insert
	public fHeapNode Insert(fHeapNode node) {
		if (root == null) {
			root = node;
			root.left = node;
			root.right = node;
			// root.child = null;
			root.parent = null;
			return root;
		}
		fHeapNode right = root.right;
		// fHeapNode left = root.left;
		node.left = root;
		node.right = right;
		root.right = node;
		right.left = node;
		// node.child = null;
		node.parent = null;
		if (node.node.getNodeValue() < root.node.getNodeValue()) {
			root = node;
		}
		return root;
	}
}

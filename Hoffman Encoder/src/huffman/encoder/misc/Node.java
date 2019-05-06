package huffman.encoder.misc;

import java.util.ArrayList;

/**
 * @author Oge Tasie
 * @Description A node class is added to to a tree during encoding. The node has
 *              two pointers to Node. The left and the right parent nodes point
 *              to a single child. Node also contains a list of chars with their
 *              char values and combined frequency of each of these elements.
 * 
 *              Huffman encoding creates a binary tree by combining characters,
 *              starting from the characters with the lowest frequency.
 *
 */
public class Node implements Comparable<Node> {
	// INITIALIZE
	Node left;
	Node right;
	Node parent;

	ArrayList<Character> list;

	int total;

	// CONSTRUCTOR
	public Node(Node left, Node right) {
		// Set the parent nodes.
		setLeft(left);
		setRight(right);
		setParent(null);

		// Initialise.
		total = 0;
		list = new ArrayList<Character>();

		// Add the contents of the parent nodes.
		if (left != null && right != null) {
			for (Character c : left.getList()) {
				addToList(c);
			}
			for (Character c : right.getList()) {
				addToList(c);
			}
			setTotal(left.getTotal() + right.getTotal());
		}
	}

	// Getters and Setters
	// Returns the linked nodes.
	public Node getLeft() {
		return left;
	}

	public Node getRight() {
		return right;
	}

	// Sets the linked nodes.
	public void setLeft(Node left) {
		this.left = left;
	}

	public void setRight(Node right) {
		this.right = right;
	}

	public Node getParent() {
		return parent;
	}

	public void setParent(Node parent) {
		this.parent = parent;
	}

	// Get the total frequency of all characters in this node.
	public int getTotal() {
		return total;
	}

	// Set the frequency of all characters in this node.
	public void setTotal(int total) {
		this.total = total;
	}

	// Get the current list of all characters in this node.
	public ArrayList<Character> getList() {
		return list;
	}

	// Adds the frequency and value of a newly added node.
	public void addToList(char c) {
		this.list.add(c);
	}

	@Override
	public int compareTo(Node node) {
		return this.total - node.total;
	}
}

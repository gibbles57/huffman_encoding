import java.io.Serializable;

/**
 * A tree which each parent can have at most 2 children.
 */
public class BinaryTree implements Serializable {
	
	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	
	/**
	 * Root of the tree.
	 */
	public TreeNode root;
  	
	/**
  	 * Sets the root of the tree.
  	 * @param node to be set as the root.
  	 */
	public void setRoot(TreeNode node){
		this.root = node;
	}
	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	/**
	 * Gets the height of the tree.
	 * @return height of the tree.
	 */
	public int height(){
		// Return the height of the tree.
		// Return -1 for a null tree
		// 
		// Hint: this is doable in _very_ few lines of code 
		//       if you choose to use recursion.
		//
		// O(N): N as the tree size   
		return height(root); //default return: change or remove as needed
	}

	/**
	 * Gets the height starting from any node of the tree.
	 * @param node acting head of the tree.
	 * @return height of the node.
	 */
	private int height(TreeNode node) {
		if (node == null) {
			return -1;
		}
		return 1 + ((height(node.left) > height(node.right)) ? height(node.left) : height(node.right));
	}
	
	/**
	 * Counts the number of leaves on the tree.
	 * @return number of leaves.
	 */
	public int numLeaves(){
		// Return the number of leaf nodes in the tree.
		// Return zero for null trees.
		// 
		// Hint: this is doable in _very_ few lines of code 
		//       if you choose to use recursion.
		//
		// O(N): N is the tree size
		return numLeaves(root); //default return: change or remove as needed
	}

	/**
	 * Counts the number of leaves from a specific node.
	 * @param node to count leaves from.
	 * @return number of leaves from the node.
	 */
	private int numLeaves(TreeNode node) {
		if (node == null) {
			return 0;
		}
		if (node.left == null && node.right == null) {
			return 1;
		}
		return numLeaves(node.left) + numLeaves(node.right);
	}
	
	/**
	 * Gets a string-form of the tree in pre-order direction.
	 * @return string of nodes of the tree.
	 */
	public String toStringPreOrder(){
		// Return a string representation of the tree
		// follow PRE-ORDER traversal to include all nodes.

		// Return empty string "" for null trees.
		// Use the toString() method of TreeNode class.
		// Check main method below for examples.

		// Hint: this is doable in _very_ few lines of code 
		//       if you choose to use recursion.
		
		return toStringPreOrder(root); //default return: change or remove as needed
	}

	/**
	 * Gets the string-form of a specific node as head in pre-order direction.
	 * @param node acting head of the tree.
	 * @return string of nodes of the acting head.
	 */
	private String toStringPreOrder(TreeNode node) {
		if (node == null) {
			return "";
		}
		return node.toString() + toStringPreOrder(node.left) + toStringPreOrder(node.right);
	}
	
	/**
	 * Gets the string-form of the tree in in-order direction.
	 * @return string of nodes of the head.
	 */
	public String toStringInOrder(){
		// Return a string representation of the tree
		// follow IN-ORDER traversal to include all nodes.

		// Return empty string "" for null trees.
		// Use the toString() method of TreeNode class.
		// Check main method below for examples.

		// Hint: this is doable in _very_ few lines of code 
		//       if you choose to use recursion.
		//

		return toStringInOrder(root); //default return: change or remove as needed
	}

	/**
	 * Gets the string-form of a specific node as head in in-order direction.
	 * @param node acting head of the tree.
	 * @return string of nodes of the acting head.
	 */
	private String toStringInOrder(TreeNode node) {
		if (node == null) {
			return "";
		}
		return toStringInOrder(node.left) + node.toString() + toStringInOrder(node.right);
	}
	
	/**
	 * Gets the string-form of the tree in level-order direction.
	 * @return string of nodes of the tree.
	 */
	public String toStringLevelOrder(){
		// Return a string representation of the tree
		// follow LEVEL-ORDER traversal to include all nodes.

		// Return empty string "" for null trees.
		// Use the toString() method of TreeNode class.
		// Check main method below for examples.
		
		// Hint: Remember that you can create a local class 
		// to help you with this!

		// [Hint]Possible approach 1:
		// It is easy to make a priority queue into a FIFO queue 
		// if you think a little bit about it. Reuse your priority 
		// queue here to do the level-order traversal. 
		
		// [Hint]Possible approach 2:
		// It is also easy to reuse the linked list class from 
		// Project 2 to implement a FIFO queue and help with the 
		// level-order traversal.
		if (root == null) {
			return "";
		}
		/**
		 * Local class to wrap the priority queue into a FIFO queue.
		 */
		class Fifo implements Comparable<Fifo> {
			TreeNode node;
			int time;
			/**
			 * Fifo constructor.
			 * @param node to set.
			 * @param time time added.
			 */
			Fifo(TreeNode node, int time) {
				this.node = node;
				this.time = time;
			}
			@Override
			public int compareTo(Fifo other) {
				return Integer.compare(this.time, other.time);
			}
		}
		PriorityQueue<Fifo> p = new PriorityQueue<>();
		int timer = 0;
		String result = "";
		p.add(new Fifo(root, timer++));
		while (p.size() > 0) {
			Fifo current = p.remove();
			TreeNode walker = current.node;
			result += walker.toString();
			if (walker.left != null) {
				p.add(new Fifo(walker.left, timer++));
			}
			if (walker.right != null) {
				p.add(new Fifo(walker.right, timer++));
			}
		}
		return result; //default return: change or remove as needed
	}

	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	/**
	 * Entry to the program.
	 * @param args command-line arguments.
	 */
	public static void main(String[] args){
	
		BinaryTree tree = new BinaryTree();
		
		//a single-node tree
		tree.setRoot(new TreeNode(1, 'r'));
		if (tree.height() == 0 && tree.numLeaves() == 1 
				&& tree.toStringPreOrder().equals("<r,1>")){
			System.out.println("Yay1");
		}

		//set up a tree
		//        r,1
		//       /   \
		//     a,2    e,10
		//   /     \
		// b,3     c,4
		//           \
		//           d,5
		// Note: this tree is a general binary tree but not a Huffman tree.
		TreeNode node1 = new TreeNode(2, 'a');
		TreeNode node2 = new TreeNode(3, 'b');		
		TreeNode node3 = new TreeNode(4, 'c');
		TreeNode node4 = new TreeNode(5, 'd');
		TreeNode node5 = new TreeNode(10, 'e');
		tree.root.setLeft(node1);
		tree.root.setRight(node5);
		node1.setLeft(node2);
		node1.setRight(node3);
		node3.setRight(node4);
		
		//tree basic features
		if (tree.root.left.right.count == 4 && tree.height() == 3 && tree.numLeaves() == 3){
			System.out.println("Yay2");
		}
		
		//tree traverals
		if (tree.toStringPreOrder().equals("<r,1><a,2><b,3><c,4><d,5><e,10>")){
			System.out.println("Yay3");
		}

		if (tree.toStringInOrder().equals("<b,3><a,2><c,4><d,5><r,1><e,10>")){
			System.out.println("Yay4");
		}
		
		if (tree.toStringLevelOrder().equals("<r,1><a,2><e,10><b,3><c,4><d,5>")){
			System.out.println("Yay5");
		}
	}	
}

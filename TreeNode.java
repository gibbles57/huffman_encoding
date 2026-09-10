//-------------------------------------------------------------
// DO NOT EDIT ANYTHING FOR THIS CLASS EXCEPT TO ADD JAVADOCS
//-------------------------------------------------------------
import java.io.Serializable;

//Tree node used in a binary huffman tree

/**
 * Tree node used in binary huffman tree.
 */
public class TreeNode implements Serializable, Comparable<TreeNode> {

	//bad practice to have public inst. variables, 
	//but we want to test this more easily...
	
	//count for the character (leaf node) or 
	//total of counts from both children (internal node)
	/**
	 * Count of the frequency of the character.
	 */
	public int count;
	
	//character represented by this node
	//internal node: keep character to be null	
	/**
	 * Character of the node.
	 */
	public Character character = null;
	
	//children links
	/**
	 * Children nodes of the current node.
	 */
	public TreeNode left, right;
	
	/**
	 * Sets the count of the node.
	 * @param count of the character.
	 */
	public TreeNode(int count){
		this.count = count;
	}
	
	/**
	 * Sets the count and character of the node.
	 * @param count of the character.
	 * @param character of the node.
	 */
	public TreeNode(int count, Character character){
		this.count = count;
		this.character = character;
	}

	/**
	 * Sets the left child of the node.
	 * @param left child of this node.
	 */
	public void setLeft(TreeNode left){ this.left = left;}

	/**
	 * Sets the right child of the node.
	 * @param right child of this node.
	 */
	public void setRight(TreeNode right){ this.right = right;}
	
	/**
	 * Compares counts of the nodes.
	 * @param otherNode being compared to this node.
	 * @return difference between counts.
	 */
	public int compareTo(TreeNode otherNode){
		if (this.count - otherNode.count!=0){
			return (this.count - otherNode.count); //compare count
		}
		else{
			if (this.character!=null && otherNode.character!=null) {//use char to break the tie
				return (this.character - otherNode.character); 
				//same character + same count would be a tie
			}
			else{
				return (this.count - otherNode.count); 
				//null + same count would be a tie				
			}
		}		
	}
	
	/**
	 * Checks if 2 nodes are equal.
	 * @param o object to check against.
	 * @return true if equal, false otherwise.
	 */
	@Override
	public boolean equals(Object o){
		if (!(o instanceof TreeNode)){
			return false;
		}
		TreeNode otherNode = (TreeNode) o;
		return (this.compareTo(otherNode) == 0);
	}
	
	/**
	 * Gets the string of the node's character and count.
	 * @return string of the node.
	 */
	public String toString(){ 
		return "<"+this.character+","+this.count+">";			
	}
}
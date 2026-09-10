import java.util.NoSuchElementException;
import java.util.Iterator;

/**
 * Implements the priority queue framework.
 * @param <T> type of the items in queue.
 */
public class PriorityQueue<T extends Comparable<T>> implements Iterable<T> {

	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	/**
	 * Head of the queue.
	 */
	private Node<T> head = null;
	
	//provided linked list node class
	/**
	 * Creates a node object.
	 * @param <T> type of the node.
	 */
	private static class Node<T> {
		/**
		 * Value of the node.
		 */
		private T value;
		
		/**
		 * Next node in the queue.
		 */
		private Node<T> next;
		
		/**
		 * Initializes node with value.
		 * @param value of the node.
		 */
		public Node(T value) { this.value = value; }
	}
		
	//provided toString() method using the iterator
	/**
	 * Gets the string of the node.
	 * @return string of the node.
	 */
	public String toString(){
		StringBuilder builder = new StringBuilder("");
		for (T value : this){
			builder.append(value);
			builder.append(" ");
		}
		return builder.toString().trim();
	}
	
	//provided iterator, if your code is working, this should
	//work too...
	/**
	 * Checks if queue is a proper priority queue.
	 * @return true if so, false otherwise.
	 */
	public Iterator<T> iterator() {
		return new Iterator<T>() {
			Node<T> current = head;
			
			public T next() {
				if(!hasNext()) {
					throw new NoSuchElementException();
				}
				T val = current.value;
				current = current.next;
				return val;
			}
			
			public boolean hasNext() {
				return (current != null);
			}
		};
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	
	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	/**
	 * Size of the queue.
	 */
	private int size = 0;
	
	/**
	 * Constructor for initializing the queue.
	 */
	public PriorityQueue() {
		//Constructor
		//initializing members if needed
		this.head = null;
		this.size = 0;
	}
	
	/**
	 * Gets the number of elements in the queue.
	 * @return number of elements.
	 */
	public int size(){
		//Return the number of elements in the priority queue
		//O(1)
		return this.size; //default return: change or remove as needed
	}
	
	/**
	 * Adds a value into the queue based on its priority.
	 * @param value of the node.
	 */
	public void add(T value) {
		//Add a value into the priority queue.  Use the value 
		//as its priority.
		
		//The priority queue must be organized as a sorted singly
		//linked list.  No dummy nodes.  
		
		//Hint: you will need to decide a way to store/sort the values
		// so that the remove/element methods can also meet the required 
		// behavior and big-O in time. Do check the requirements of 
		// remove()/element() below before you code this method.
		
		//O(n) where n is the number of items in queue.
		Node<T> node = new Node<>(value);
		if (head == null || value.compareTo(head.value) < 0) {
			node.next = head;
			head = node;
		} else {
			Node<T> walker = head;
			while (walker.next != null && value.compareTo(walker.next.value) >= 0) {
				walker = walker.next;
			}
			node.next = walker.next;
			walker.next = node;
		}
		size += 1;
	}

	/**
	 * Removes the next node in the queue.
	 * @return value of the node removed, or null if no nodes to remove.
	 */
	public T remove() {
		// Remove and return the value with the minimal priority value.
		// If two or more items are of the same priority, keep their order 
		// as FIFO, i.e. the one that was added earlier should be removed first.
		// Check main() below for examples.
			
		// Throw NoSuchElementException if queue is empty. 
		// Use this _exact_ error message for the exception
		// (quotes are not part of the message):
		//    "Priority queue empty!"

		//O(1)
		if (head == null) {
			throw new NoSuchElementException("Queue empty.");
		}
		T val = head.value;
		head = head.next;
		size -= 1;
		return val; //default return: change or remove as needed

	}
	
	/**
	 * Gets the value of the next element in the queue.
	 * @return value of next element, or null if no element exists.
	 */
	public T element() {
		// Return (but do not remove) the value with the minimal priority value.
		// If two or more items are of the same priority, keep the order 
		// as FIFO, i.e. the one that was added earlier should be reported.
		// Check main() below for examples.
		
		// Throw NoSuchElementException if queue is empty. 
		// Use this _exact_ error message for the exception
		// (quotes are not part of the message):
		//    "Priority queue empty!"

		//O(1)
		if (head == null) {
			throw new NoSuchElementException("Queue empty.");
		}
		return head.value; //default return: change or remove as needed

	}

	/**
	 * Checks if a value is already in a queue.
	 * @param value to check for.
	 * @return true is value is in queue, false otherwise.
	 */
	public boolean contains(T value){
		// Return true if value is present in queue; 
		// return false otherwise.
		
		// Hint: remember to use .equals() for comparison.
		
		//O(n) where n is the number of items in queue.
		Node<T> walker = head;
		while (walker != null) {
			if (walker.value.equals(value)) {
				return true;
			}
			walker = walker.next;
		}
		return false; //default return: change or remove as needed
	
	}
	
	//-------------------------------------------------------------
	// Main Method For Your Testing -- Edit all you want
	//-------------------------------------------------------------
	
	/**
	 * Entry to the program.
	 * @param args command-line arguments.
	 */
	public static void main(String[] args){
		PriorityQueue<Character> letters = new PriorityQueue<>();
				
		//add/size/element/contains
		String chars = "MASON";
		for (int i=0; i<5; i++){
			letters.add(chars.charAt(i));
		}
			
		if (letters.size() == 5 && letters.element() == 'A' 
			&& letters.contains('O') && !letters.contains('B')){
			System.out.println("Yay 1");
		}
				
		//remove
		if (letters.remove() == 'A' && letters.size() == 4 && letters.element() == 'M'){
			System.out.println("Yay 2");
		}
		
		//sequence of add/remove
		PriorityQueue<Integer> nums = new PriorityQueue<>();
		for (int i=0; i<10; i++){
			int val = (i*i) % 17;
			nums.add(val);
		}
		boolean ok = nums.toString().trim().equals("0 1 2 4 8 9 13 13 15 16");
		StringBuilder output = new StringBuilder();
		for (int i=0; i<10; i++){
			int val = nums.remove();
			output.append(val);
			output.append(" ");
		}
		if (ok && output.toString().trim().equals("0 1 2 4 8 9 13 13 15 16")){
			System.out.println("Yay 3");		
		}
		
		//values added with the same priority are kept in FIFO order
		PriorityQueue<String> msgs = new PriorityQueue<>();
		String msg1 = new String("Hello");
		String msg2 = new String("Hello");
		msgs.add(msg1);
		msgs.add(chars);
		msgs.add(msg2);
		if (msgs.toString().trim().equals("Hello Hello MASON") && 
			msgs.contains(msg1) && msgs.contains(msg2) &&
			msgs.element()==msg1 && msgs.remove() != msg2){  //use of "==" is intentional here
			System.out.println("Yay 4");	
		}
	
		
	}
	
}
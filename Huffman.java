import java.io.*;

/**
 * Implements Huffman Tree encoding.
 */
class Huffman implements Serializable{
	// Note: We define this class (and a couple of other classes of 
	// this project) as Serializable in order to be able to save 
	// the Huffman Object into a file for encoding/decoding. 
	// (See main method below for details.)
	// You do not need to do anything special in your implementation 
	// for this. When a serializable object gets output into a file, 
	// "transient" members will be skipped.
	
	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------

	//default length used to create hashtables
	/**
	 * Base length of hashtables.
	 */
	public static final int DEFAULT_TABLE_LENGTH = 11;
	
	//original input string to encode
	/**
	 * Original input stirng to encode.
	 */
	private transient String inputContents = null;
	
	//hashtable used to count the frequencies of input characters
	/**
	 * Counts the frequencies of input characters.
	 */
	private transient HashTable<Character,Integer> counts = 
		new HashTable<Character,Integer>(DEFAULT_TABLE_LENGTH);
		
	//priority queue used to build huffman tree
	/**
	 * Priority queue to build tree.
	 */
	private transient PriorityQueue<TreeNode> queue = new PriorityQueue<>();
	
	//huffman tree
	/**
	 * Creates the huffman tree.
	 */
	private BinaryTree huffmanTree = new BinaryTree();
	
	//hashtable used to record the encoding for input characters
	/**
	 * Records the encodings.
	 */
	private HashTable<Character,String> encodings = new HashTable<>(DEFAULT_TABLE_LENGTH);
		
	//setters and getters to help testing
	/**
	 * Sets the counts of the characters.
	 * @param counts of the characters.
	 */
	public void setCounts(HashTable<Character, Integer> counts){
		this.counts = counts;
	}
	
	/**
	 * Gets the counts of the characters.
	 * @return hashtable of counts of characters.
	 */
	public HashTable<Character,Integer> getCounts(){
		return counts;
	}
	
	/**
	 * Sets the queue.
	 * @param queue to be set.
	 */
	public void setQueue(PriorityQueue<TreeNode> queue){
		this.queue = queue;
	}

	/**
	 * Gets the queue.
	 * @return the queue.
	 */
	public PriorityQueue<TreeNode> getQueue(){
		return queue;
	}

	/**
	 * Sets the huffman tree.
	 * @param huffmanTree the huffman tree to be set.
	 */
	public void setTree(BinaryTree huffmanTree){
		this.huffmanTree = huffmanTree;
	}

	/**
	 * Gets the huffman tree.
	 * @return the huffman tree.
	 */
	public BinaryTree getTree(){
		return huffmanTree;
	}

	/**
	 * Gets the encodings of the characters.
	 * @return encodings of the characters.
	 */
	public HashTable<Character,String> getEncodings(){
		return encodings;
	}

	//provided methods for encoding
	//generate the encoding result from the huffman tree
	//if you have constructed a correct huffman tree, 
	// this would work...
	/**
	 * Gets the encodings of the tree.
	 */
	public void computeEncodings(){
		computeEncodings(huffmanTree.root,"");
	}

	//recursive helper method for encoding
	/**
	 * Encodes the nodes of the tree.
	 * @param currentLoc the current node.
	 * @param encoding of the nodes.
	 */
	private void computeEncodings(TreeNode currentLoc, String encoding){
		if(currentLoc.character != null){
			this.encodings.put(currentLoc.character, encoding);
		}
		else{
			computeEncodings(currentLoc.left, encoding+"0");
			computeEncodings(currentLoc.right, encoding+"1");
		}
	}

	// Use the encoding hashtable to generate a string of 
	// 0's and 1's as the encoding of the input.
	// The input might have multiple characters.
	/**
	 * Generates an encoded string of bits.
	 * @param input to encode.
	 * @return the encoded string.
	 */
	public String encode(String input){

		StringBuffer output = new StringBuffer();
		
		for (char ch : input.toCharArray()){
			output.append(this.encodings.get(ch));
		}
		
		return output.toString();	
	
	}

	//After encodings are computed, encode inputContents
	/**
	 * Encode the inputContents.
	 * @return the encoded inputContents.
	 */
	public String encode(){

		StringBuffer output = new StringBuffer();
		
		for (char ch : inputContents.toCharArray()){
			output.append(this.encodings.get(ch));
		}
		
		return output.toString();	
	
	}

	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------

	// ADD MORE PRIVATE MEMBERS HERE IF NEEDED!

	/**
	 * Constructor for the huffman tree.
	 * @param input of the tree.
	 */
	public Huffman(String input){
		//Constructor
		
		// Set inputContents to be input.
		// Perform other initializations if needed.
		this.inputContents = input;
	}

	/**
	 * Counts frequency of the characters.
	 */
	public void createCounts(){
		//Step 1 of Huffman's algorithm:
		// Count the number of occurrences of each character
		// in inputContents; store the result in hashtable counts.
				
		// Always start with an empty hashtable.
		counts = new HashTable<Character, Integer>(DEFAULT_TABLE_LENGTH);
		if (inputContents == null) {
			return;
		}
		for (char c : inputContents.toCharArray()) {
			Integer count = counts.get(c);
			if (count == null) {
				counts.put(c, 1);
			} else {
				counts.put(c, count + 1);
			}
		}
	}
	
	/**
	 * Creates the queue of the characters.
	 */
	public void initQueue(){
		//Step 2 of Huffman's algorithm:
		// For each character from inputContents, use the 
		// frequency information from hashtable counts 
		// (constructed in step 1) to create one leaf 
		// TreeNode. Add the node into the priority queue.
		
		// Follow the original order of inputContents to 
		// process characters and add nodes in one by one. 
		// Make sure no duplicates are added into the priority 
		// queue: only one node for each character.  
		// Hint: use contains() of PriorityQueue class; or use
		//  a separate HashTable to help you to avoid duplicates.
				
		// Always start with an empty priority queue.
		queue = new PriorityQueue<>();
		HashTable<Character, Boolean> added = new HashTable<>(DEFAULT_TABLE_LENGTH);
		for (char c : inputContents.toCharArray()) {
			if (added.get(c) == null) {
				int f = counts.get(c);
				TreeNode node = new TreeNode(f, c);
				queue.add(node);
				added.put(c, true);
			}
		}
	}
	
	/**
	 * Builds the tree based on the queue.
	 */
	public void buildTree(){
		//Step 3 of Huffman's algorithm:
		// Starting form the priority queue initialized in Step 2, 
		// merge nodes together into a single Huffman encoding tree.

		// You can assume that the queue has at least two leaf 
		// nodes to start.		
		
		// In each merging step, create a new node as the parent of two
		// nodes removed from the priority queue. Make sure the first node 
		// you get from the priority queue is the left child; the second node 
		// from the priority queue is the right child.
		while (queue.size() > 1) {
			TreeNode left = queue.remove();
			TreeNode right = queue.remove();
			TreeNode parent = new TreeNode(left.count + right.count, null);
			parent.left = left;
			parent.right = right;
			queue.add(parent);
		}
		if (queue.size() == 1) {
			huffmanTree.setRoot(queue.remove());
		}
	}
	
	/**
	 * Decodes the Huffman encoding from the input.
	 * @param input to be decoded.
	 * @return the decoded string.
	 */
	public String decode(String input){
		//Step 4 of Huffman's algorithm:
		// Use the constructed Huffman tree from step 3 to decode 
		// the input string of 1s and 0s. 
		// The input string might contain the encodings of 
		// more than one character.
				
		//Hints:
		//	(1) To break the string into a character array (char[]), use:
		//		input.toCharArray()
		//	(2) To get the numeric value of a character, use:
		//		Character.getNumericValue(ch)
		//	(3) Remember to start over at the root when you find a
		//		valid character.
		if (huffmanTree.root == null || input == null) {
			return "";
		}
		StringBuilder decoded = new StringBuilder();
		TreeNode walker = huffmanTree.root;
		for (char b : input.toCharArray()) {
			if (b == '0') {
				walker = walker.left;
			} else {
				walker = walker.right;
			}
			if (walker.left == null && walker.right == null) {
				decoded.append(walker.character);
				walker = huffmanTree.root;
			}
		}
		return decoded.toString(); //default return: change or remove as needed

	}

	//-------------------------------------------------------------
	// PROVIDED TESTING CODE: FEEL FREE TO EDIT
	//-------------------------------------------------------------
	/**
	 * Testing of the encoding/decoding.
	 */
	public static void testMain(){
	
		Huffman huff = new Huffman("cabbeadcdcdcdbbd");
		
		//step 1: count frequency 
		huff.createCounts();
		HashTable<Character,Integer> counts = huff.getCounts();
		//System.out.println(counts);
		//System.out.println(counts.toStringDebug());

		if (counts.size() == 5 && counts.get('a') == 2 && counts.get('e')==1
			&& counts.toString().equals("c:4\nd:5\ne:1\na:2\nb:4")){
			System.out.println("Yay 1");
		}
		
		//step 2: initialize priority queue with leaf nodes
		huff.initQueue();
		PriorityQueue<TreeNode> queue = huff.getQueue();
		//System.out.println(queue);
		if (queue.size() == 5 && queue.element().character=='e' && queue.element().count==1){
			System.out.println("Yay 2");		
		}
		
		if (queue.toString().equals("<e,1> <a,2> <b,4> <c,4> <d,5>")){
			System.out.println("Yay 3");				
		}
		
		//step 3: build huffman tree with the help of priority queue
		huff.buildTree();
		BinaryTree tree= huff.getTree();
		if (tree.root.count == 16 && tree.root.left.count == 7 & tree.root.right.count == 9){
			System.out.println("Yay 4");					
		}
		
		//System.out.println(tree.toStringPreOrder());
		if (tree.toStringPreOrder().equals("<null,16><null,7><null,3><e,1><a,2><b,4><null,9><c,4><d,5>")){
			System.out.println("Yay 5");					
		}
		
		//step 4: encoding and decoding
		huff.computeEncodings();
		//System.out.println(huff.getEncodings());
		if (huff.decode("1000101").equals("cab") && huff.encode("cab").equals("1000101")){
			System.out.println("Yay 6");							
		}
		
	}	
	//-------------------------------------------------------------
	// END OF TESTING CODE
	//-------------------------------------------------------------


	//-------------------------------------------------------------
	// DO NOT EDIT ANYTHING FOR THIS SECTION EXCEPT TO ADD JAVADOCS
	//-------------------------------------------------------------
	//--------------------------------------------------------------------------------
	// How to run:
	// - To run testMain: java Huffman
	// - To encode:  java Huffman -e fileToEncode encodedOutputFile HuffmanObjectOutputFile
	// - To decode:  java Huffman -d fileToDecode decodedOutputFile HuffmanObjectInputFile
	//--------------------------------------------------------------------------------

	/**
	 * Entry to the program.
	 * @param args command-line arguments.
	 */
	public static void main(String[] args)
	{
		// no command-line args: provided testing of Huffman's algorithm
		if (args.length==0){
			testMain();
			return;
		}
		
		// with command-line args: file I/O for encoding/decoding
		if(args[0].equals("-e") && (args.length < 4 || args.length > 4)){
			System.out.println("Usage: java Huffman -e fileToEncode encodedOutputFile HuffmanObjectOutputFile");
			return;
		}
		else if(args[0].equals("-d") && (args.length < 4 || args.length > 4)){
			System.out.println("Usage: java Huffman -d fileToDecode decodedOutputFile HuffmanObjectInputFile");
			return;
		}
		else if(!args[0].equals("-d") && !args[0].equals("-e")){
			System.out.println("Usage: java Huffman -[e|d]");
			return;
		}
		
		String fileAsString;
		Huffman huff;
		try{
			switch(args[0]){
				case "-e": //encoding
				
					//read in fileToEncode
					fileAsString = getFileContents(args[1]);
					//System.out.println(fileAsString);
					
					//Huffman's algorithm 
					huff = new Huffman(fileAsString);
					huff.createCounts(); //step 1
					//System.out.println(hTree.counts);
					huff.initQueue(); //step 2
					//System.out.println(hTree.queue);
					huff.buildTree(); //step 3
					
					huff.computeEncodings();
					
					//encoding
					String encoding = huff.encode();
					//System.out.println("Encoded: " + encoding);
					
					//output encoded contents as a sequence of bits into file
					writeEncodedMessage(encoding, args[2]);
					
					//output Huffman object into file
					writeEncodedObject(huff, args[3]);
					break;
					
				case "-d": //decoding
				
					//read in from file and construct Huffman object
					huff = getEncodedObject(args[3]);
					
					//read in from file the encoded bits and
					//convert into a string (with only characters '0' and '1')
					fileAsString = getFileBinaryContents(args[1]);
					//System.out.println(fileAsString);
					
					//decoding
					String decodedMessage = huff.decode(fileAsString);
					
					//output decoded contents into file
					writeDecodedMessage(decodedMessage, args[2]);
					break;
			}
		}
		catch(IOException e){
			System.out.println("Problem reading or writing to specified file");
			System.out.println(e.toString());
		}
		
	}
	
	// output a Huffman Object to a file 
	/**
	 * Output Huffman object to file.
	 * @param huff huffman tree.
	 * @param filename output file.
	 * @throws IOException if error with file.
	 */
	public static void writeEncodedObject(Huffman huff, String filename) throws IOException{
		try(ObjectOutputStream output = new ObjectOutputStream(new FileOutputStream(filename))){
			output.writeObject(huff);
		}
	}

	// read from a file and create a Huffman Object based on the file contents
	/**
	 * Reads from a file and creates huffman tree.
	 * @param filename of the input file.
	 * @return huffman object.
	 * @throws IOException if error with file.
	 */
	public static Huffman getEncodedObject(String filename) throws IOException{
		try(ObjectInputStream input = new ObjectInputStream(new FileInputStream(filename))){
			return (Huffman)input.readObject();
		}
		catch(ClassNotFoundException e){
			throw new IOException("Can not read class from provided file.");
		}
	}
	
	// read the encoding result (as a string of 0's and 1's) from a file
	/**
	 * Reads encoding from file.
	 * @param filename to read from.
	 * @return the contents of the file.
	 * @throws IOException if error with file.
	 */
	public static String getFileBinaryContents(String filename) throws IOException{
		StringBuffer fileContents = new StringBuffer();
		try(BitInputStream bs = new BitInputStream(new FileInputStream(filename), true)){
			while(bs.hasNextBit()){
				fileContents.append(bs.readBit());
			}
		}
		return fileContents.toString();
	}

	// output the encoding result (a string of 0's and 1's) as a bit sequence into a file
	/**
	 * Outputs encoding result to a file.
	 * @param message of the encoding.
	 * @param filename to write to.
	 * @throws IOException if error with file.
	 */
	public static void writeEncodedMessage(String message, String filename) throws IOException{
		try(BitOutputStream bs = new BitOutputStream(new FileOutputStream(filename), true)){
			bs.writeBits(message);
		}
	}

	
	//read from file and return file contents as a string
	/**
	 * Gets the file contents as a string.
	 * @param filename of the file to read from.
	 * @return string of the file contents.
	 * @throws IOException if error with file.
	 */
	public static String getFileContents(String filename) throws IOException{
		StringBuffer fileContents = new StringBuffer();
		try(BufferedReader br = new BufferedReader(new FileReader(filename))){
			String input = br.readLine();
			fileContents.append(input);
			input = br.readLine();
			
			while(input != null){
				fileContents.append("\n" + input);
				input = br.readLine();
			}
		}
		
		return fileContents.toString();
	}

	// out put message as a sequence of bits to file
	/**
	 * Writes the decoded message as bits to a file.
	 * @param message decoded message.
	 * @param filename to write to.
	 * @throws IOException if error with file.
	 */
	public static void writeDecodedMessage(String message, String filename) throws IOException{
		try(BufferedWriter br = new BufferedWriter(new FileWriter(filename))){
			br.write(message);
		}
	}
	
	//-------------------------------------------------------------
	// END OF PROVIDED "DO NOT EDIT" SECTION 
	//-------------------------------------------------------------
	

}
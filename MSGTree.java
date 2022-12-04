package edu.iastate.cs228.hw4;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * @author Haakon H
 * A class to create a binary tree to be decoded. 
 */
public class MSGTree {
	public Node root; // root of tree

	private String decode; // the binary code to make the string

	private String sequence; // the sequence of build

	private ArrayList<String> instuct = new ArrayList<>(); //so keep track of codes
	
	private String word = ""; //the word that got decoded
	/**
	 * class to hold the tree methods like add(recursive), Node root, build(in
	 * constructor).
	 */
	public MSGTree(String sequence, String decode) { 
		this.decode = decode;
		this.sequence = sequence;
		build(sequence); //auto build
		toString(); // to get the code
	}

	/**
	 * builds the tree by creating a root and then following a given sequence.
	 * 
	 * @param sequence
	 * @return string of L or R depending on where it was added
	 */
	private String build(String sequence) {
		root = new Node('^'); 
		String s = "";

		for (int i = 1; i < sequence.length(); i++) {
			root.check(); //before an add it update the full state of all nodes 
			s += root.add(sequence.charAt(i)); //adds to root that is recursive
		}
		
		return s;
	}
	
	/**
	 * given a Binary Sequence moves through the tree and builds a string
	 * 
	 * @param Binary Sequence
	 * @return End Word
	 */
	@Override
	public String toString() {
		Node current = root;
		String in = "";
		word = "";
		
		for (int i = 0; i < decode.length(); i++) { //go through the decode string
			if(decode.charAt(i) == '0' && current.hasLeft()) { //has a left and wants to go left then go left
				in += "" + decode.charAt(i);
				current = current.left;
				if(i == decode.length() - 1) { //for the final add to the word 
					word += "" + current.data;
					current = root; 
					instuct.add(in);
					in = "";
				}
			}
			else if(decode.charAt(i) == '1' && current.hasRight()) { //has right and wants to go right, goes right
				in += "" + decode.charAt(i);
				current = current.right;
				if(i == decode.length() - 1) {//for the final add to the word 
					word += "" + current.data;
					current = root; 
					instuct.add(in); //add code to list
					in = "";
				}
			}
			else {
				i--;
				if(current.data == '\\'){
					word += "\n";
				}else {
					word += "" + current.data;
				}
				current = root; 
				instuct.add(in);
				in = "";
			}
		}
		return word;

	}

	/**
	 * prints the code to each char
	 * 
	 * @return string that is code
	 */
	public void printCodes() {
		// TODO gives the codes to get char
		HashMap<String, String> map = new HashMap<String, String>(); 
		
		for (int i = 0; i < word.length(); i++) { //making the map connecting code to letters
			if(!map.containsKey(instuct.get(i))) {
				map.put(instuct.get(i), "" + word.charAt(i));
			}
		}
		
		Set<String> set = new HashSet<>(instuct); //no dup codes
		instuct.clear();
		instuct.addAll(set);
		
		System.out.println("\nLetter    Code"); 
		
		for(int i = 0; i < instuct.size(); i++) { //to print through the map
			System.out.println(map.get(instuct.get(i)) + "         " + instuct.get(i));
			
		}
	}

	/**
	 * Node to hold data and the left and right node
	 */

	public class Node {
		/*
		 * left, right, data
		 */

		public Node left; // next left

		public Node right; // next right

		public char data; // data of node
		
		boolean full;

		/**
		 * sets left and right to null and the data is saved
		 * 
		 * @param data
		 */
		Node(char data) { // easy constructor
			this(null, null, data);
		}

		/**
		 * sets the left and right to given Left and Right and saves the data
		 * 
		 * @param data
		 */
		Node(Node left, Node right, char data) { // more complex constructor
			this.left = left;
			this.right = right;
			this.data = data;
			full = data != '^'; //full if it is not a branch
		}

		/**
		 * adds the char value given to the proper spot by recurivly moving left and
		 * right
		 * 
		 * @param char
		 * @return char of L or R depending on added Left or Right. if N then char was not added to word
		 */
		public String add(char c) {	//recursive add going through the list finding an empty spot
		if(hasLeft() && left.data == '^' && !left.full) { //has left, current is branch, and left isnt full then go left
			return left.add(c);
		}
		else if (hasRight() && right.data == '^' && !right.full) { //has right, current is branch, and right isnt full then go right
			return right.add(c);
		}
		
		if (data == '^') { //for adding L or R
			   if (left == null) {
					left = new Node(c);
					return "L";
				} else if (right == null) {
					right = new Node(c);
					return "R";
				}
			}
		return "N";
		}
		
		/**
		 * check is recursive method that updates the full states 
		 */
		private void check() {
			if(hasLeft()){
				left.check();
			}
			if(hasRight()) {
				right.check();
			}
			
			if(hasRight()) { //if it has right then it has to have a left
				if(left.full && right.full) { //if both are full then this branch has to be full
					full = true;
				}
			}
		}

		/**
		 * sets the data to char
		 * 
		 * @param char c
		 */
		public void set(char c) {
			data = c;
		}

		/**
		 * returns true if the left has an Obj and false if it does not have an Obj
		 * 
		 * @return a boolean
		 */
		public boolean hasLeft() {
			return left != null;
		}

		/**
		 * returns true if the right has an Obj and false if it does not have an Obj
		 * 
		 * @return a boolean
		 */
		public boolean hasRight() {
			return right != null;
		}

	}

}
package edu.iastate.cs228.hw4;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.Scanner;

public class Runner {

	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		
		System.out.println("Enter Filename");
		File f = new File(scan.next()); 
		
		String sq, decode;
		Scanner fscan = null;
		
		try {
			fscan = new Scanner(f);
		} catch (FileNotFoundException e) {
			System.out.println("File not found!");
		}
		
		sq = fscan.nextLine(); 
		decode = fscan.nextLine();
		
		fscan.close();//clean up
		scan.close();
		
		MSGTree tree = new MSGTree(sq, decode);
		
		tree.printCodes();
		
		System.out.println("\nword:\n" + tree.toString());

	}

}

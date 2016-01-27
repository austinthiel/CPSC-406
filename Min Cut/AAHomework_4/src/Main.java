/*
 * Austin Thiel
 * Algorithm Analysis
 * Homework 4
 * 10/12/2015
 * 
 * This program takes a .txt input file in the following format:
 * 
 * # // number of cases
 * # # // number of vertices, number of edges
 * # # // edge pointers
 * ...
 * # # // number of vertices, number of edges
 * # # // edge pointers
 * ...
 * 
 * It then uses an implementation of Karger's algorithm to determine the minimum cut of each inputted graph
 * 
 */

import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		long start = System.nanoTime(); // system timer start
		
		IOHandler io = new IOHandler();
		
		io.openFile();
		io.fileManager(); 
		io.closeScannerAndPrintWriter();
		
		long elapsedTime = System.nanoTime() - start; // subtract new current time from start time to obtain time elapsed in nanoseconds
		long durationInMs = TimeUnit.NANOSECONDS.toMillis(elapsedTime); // convert nanoseconds to milliseconds
		System.out.println("Total time elapsed: " + durationInMs + "ms"); // print with formatting
	}
}

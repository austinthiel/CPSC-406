import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOHandler {
	
	BlackBox bb = new BlackBox(); // create a BlackBox object for use in this class
	Scanner sc;
	PrintWriter pw;
	
	protected void openFile() throws FileNotFoundException{
		sc = new Scanner (new BufferedReader(new FileReader("C:\\hw3\\input.txt")));		
		pw = new PrintWriter("C:\\hw3\\2015843489.txt");
	}
	
	protected void fileManager(Scanner sc){ // reads, formats, calls computations, and calls prints
		
		int numCases = 0;
		int highestDeg = 0;
		int numDigits = 0;
		int currCaseNum = 0;
		
		if(sc.hasNextInt()){ // Grab first integer on line 1 of file to determine the number of cases
			numCases = sc.nextInt();
		}
		
		for(int i = 0; i < numCases; i++){ // iterate for each case
			
			currCaseNum = i + 1; // keep track of the case number we're computing
			
			if(sc.hasNextInt()){
				highestDeg = sc.nextInt(); // Grab highest degree of polynomial
				numDigits = highestDeg+1; // ex: a quadratic function 3x^2 + 2x + 1 has 3 digits, aka highest degree + 1
			}
			
			int n = 1; // lowest possible n
			while (n <= highestDeg){ // make n higher than the highest degree and a multiple of 2
				n *= 2;
			}
			
			Complex[] data = new Complex[n]; // holds polynomial + empty space

			for (int k = 0; k < n; k++){
				data[k] = new Complex(0.0,0.0); // load array with 0's so the empty space isnt null
			}
			
			for(int j = 0; j < numDigits; j++){ // add digits to array in place of some 0's
				Complex tc = new Complex(sc.nextInt(), 0.0);
				data[j] = tc;
			}
			
			appendSolution(bb.computeFFT(data), currCaseNum); // send case solution to be written to output
			
			//System.out.println(); // formatting between cases for console readability
		}
	}
	
	private void appendSolution(Complex[] solution, int currCaseNum){ // write to output file
		pw.println("#" + currCaseNum);
		for(int i = 0; i < solution.length; i++){
			//System.out.println(solution[i]); //console printing
			pw.println(solution[i]);  // print all complex numbers (solutions) from the array to the file
		}
		pw.println();
		pw.flush(); // update the file
	}
	
	protected Scanner getScanner(){
		return sc; //used to pass the scanner around
	}
	
	protected void closeScannerAndPrintWriter(){ // close up possible memory leaks
		sc.close();
		pw.close();
	}
}

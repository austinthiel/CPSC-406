import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;


public class IOHandler {
	
	private BlackBox bb = new BlackBox();
	private Scanner sc;
	private PrintWriter pw;
	private int numCases;
	
	protected void openFileIO() throws FileNotFoundException{
		sc = new Scanner (new BufferedReader(new FileReader("C:\\hw5\\input.txt"))); // input file	
		pw = new PrintWriter("C:\\hw5\\2015843489.txt"); // output file
	}
	
	protected void caseManager(){
		numCases = sc.nextInt();
		for(int i = 0; i < numCases; i++){
			bb.initArray(sc.nextInt());
			this.fillArray();
			computeTSP();
		}
	}
	
	protected void fillArray(){
		bb.loadArray(sc);
		//bb.printArray();
	}
	
	protected void computeTSP(){
		bb.compute();
	}

	protected void closeFileIO(){ // close up possible memory leaks
		sc.close();
		pw.close();
	}
}

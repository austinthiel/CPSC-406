import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.Scanner;

public class IOHandler {
	
	private BlackBox bb;
	private Scanner sc;
	private PrintWriter pw;
	private int numCases;
	private int numVertices;
	private int numEdges;
	
	protected void openFile() throws FileNotFoundException{
		sc = new Scanner (new BufferedReader(new FileReader("C:\\hw4\\input.txt"))); // input file	
		pw = new PrintWriter("C:\\hw4\\2015843489.txt"); // output file
	}
	
	protected void fileManager(){
		this.setNumCases(sc.nextInt()); // grab number of cases
		for(int i = 0; i < numCases; i++){ // start reading a case
			this.setNumVertices(sc.nextInt());
			this.setNumEdges(sc.nextInt());
			
			bb = new BlackBox();
			bb.initList(getNumVertices()); // initialize a 2d arraylist
			
			if(sc.hasNextLine()) { sc.nextLine(); }
			
			for(int j = 0; j < getNumEdges(); j++){	
				bb.loadEdge(sc.nextInt(), sc.nextInt()); // load edges into the arraylist
			}
			
			int minCut = bb.minCut(numVertices, numEdges); // run Karger's algorithm to compute the minimum cut
			pw.println(minCut); // print to output file
			pw.flush();
		}
	}
	
	private void setNumCases(int n){
		if(sc.hasNextInt()) { numCases = n; }
	}
	
	private void setNumVertices(int n){
		if(sc.hasNextInt()){ numVertices = n; }
	}
	
	private int getNumVertices(){
		return numVertices;
	}
	
	private void setNumEdges(int n){
		if(sc.hasNextInt()){ numEdges = n; }
	}
	
	private int getNumEdges(){
		return numEdges;
	}
	
	protected void closeScannerAndPrintWriter(){ // close up possible memory leaks
		sc.close();
		pw.close();
	}
}
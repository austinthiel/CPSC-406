import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;


public class IOHandler {

	private BlackBox bb = new BlackBox();
	private Scanner sc;
	private PrintWriter pw;
	
	public int numCases;
	public int numVars;
	public int numCons;
	public int numSlackVars;
	public ArrayList<Double> objList = new ArrayList<Double>();
	public ArrayList<ArrayList<Double>> conList = new ArrayList<ArrayList<Double>>();

	protected void openFileIO() throws FileNotFoundException{
		sc = new Scanner (new BufferedReader(new FileReader("C:\\hw6\\input.txt"))); // input file	
		pw = new PrintWriter("C:\\hw6\\2015843489.txt"); // output file
	}
	
	protected void caseManager(){
		
		int numCases = sc.nextInt();
		for(int i = 0; i < numCases; i++){
			numVars = sc.nextInt();
			numCons = sc.nextInt();
			numSlackVars = numCons;
			
			loadObjectiveFunc();
			loadEquations();
			setSlackVars();
			
			//printList(); // PRINT STARTING MATRIX
			
			StringBuilder solution = computeSimplex();
			
			pw.println(solution);
			pw.flush();
			
			objList.clear();
			conList.clear();
		}
	}
	
	protected void loadObjectiveFunc(){
		for(int k = 0; k < numVars; k++){ // LOADS OBJECTIVE EQUATION WITH SLACK VARS
			if(sc.hasNextInt()){
				objList.add((double)sc.nextInt());
			}else{
				objList.add(sc.nextDouble());
			}
		}
		for(int k = 0; k < numSlackVars; k++){
			objList.add((double)0);
		}
		//objList.add(1); // Z-COLUMN
		objList.add((double)0); // RHS
	}
	
	protected void loadEquations(){
		for(int k = 0; k < numCons; k++){ // LOADS CONSTRAINT EQUATIONS WITH SLACK VARS
			ArrayList<Double> list = new ArrayList<Double>();
			list.add((double) 0); // COST Cb
			for(int m = 0; m < numVars; m++){
				if(sc.hasNextInt()){
					list.add((double)(sc.nextInt()));
				}else{
					list.add(sc.nextDouble());
				}
			}
			for(int m = 0; m < numSlackVars; m++){
				list.add((double) 0);
			}
			//list.add(0); // Z-COLUMN
			list.add((double) sc.nextInt()); // RHS
			conList.add(list);
		}
		//conList.add(objList);
	}
	
	protected void setSlackVars(){ // SET CORRECT SLACK VARS TO 1
		int slackPos = numVars+1;
		for(int j = 0; j < conList.size(); j++, slackPos++){
			conList.get(j).set(slackPos, (double) 1);
		}
	}
	
	protected void printList(){
		for(int i = 0; i < conList.size(); i++){
			System.out.println(conList.get(i));
		}
	}
	
	protected StringBuilder computeSimplex(){
		return bb.compute(conList, objList, numVars, numSlackVars);
	}
	
	protected void closeFileIO(){ // close up possible memory leaks
		sc.close();
		pw.close();
	}
}

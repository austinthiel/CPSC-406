import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Scanner;
import java.util.Set;


public class FileHandler{
	
	int numCases;
	int numPoints;
	int x;
	int y;
	Point[] points = null;
	Point[] colPoints = null;
	Set<Point> solutionPoints = new HashSet<Point>();
	ArrayList<Line> lines = new ArrayList<Line>();
	int numIntersections = 0;
	int trackX;
	int trackY;
	int count = 0;
	boolean wasBad = false; // used to avoid unnecessary computations for single vertical/horizontal lines
	
	public void openFile() throws IOException{
		
		Scanner sc = new Scanner (new BufferedReader(new FileReader("C:\\hw2\\input.txt")));		
		readFile(sc);
		
		sc.close();
	}
	
	public void readFile(Scanner sc) throws IOException{ // start reading down the file while storing number of cases and points for each case
		
		PrintWriter pw = new PrintWriter("C:\\hw2\\output.txt");
		
		if(sc.hasNextLine()){
			numCases = Integer.parseInt(sc.nextLine());
			//System.out.println("numCases: " + numCases);
			if (numCases > 0){
				sc.nextLine();
			}
		}
		
		for(int i = 0; i < numCases; i++){
			if (sc.hasNextLine()){
				numPoints = Integer.parseInt(sc.nextLine());
				points = new Point[numPoints];
				//System.out.println("numPoints: " + numPoints);
			}
			for(int j = 0; j < numPoints; j++){
				if(sc.hasNextInt()){
					x = sc.nextInt();
				}
				if (sc.hasNextInt()){
					y = sc.nextInt();
				}
				
				points[j] = new Point(x,y);
				//System.out.println(points[j].toString());
				
			}
			
			trackX = points[0].getX();
			trackY = points[0].getY();
			
			for (int j = 0; j < points.length; j++){ // catch large vertical lines that will never have an intersection
				if (points[j].getX() == trackX){
					count++;
					if (count == points.length){
						outputZero(pw);
						wasBad = true;
					}
				}else{
					break;
				}
			}
			
			count = 0;
			
			for (int j = 0; j < points.length; j++){ // catch large horizontal lines that will never have an intersection
				if (points[j].getY() == trackY){
					count++;
					if (count == points.length){
						outputZero(pw);
						wasBad = true;
					}
				}else{
					break;
				}
			}
			
			count = 0;
			
			if (sc.hasNextLine()){ // formatting
				sc.nextLine();
				if (sc.hasNextLine()){
					sc.nextLine();
				}
				System.out.println();
			}
			
			if(wasBad == false){
				compute(points, pw); // take the list of points and go make lines out of them
			}
			wasBad = false;
			lines.clear(); // clear the list of lines for the next case
			System.out.println(); // formatting
		}
		pw.close();
	}
	
	public void compute(Point[] points, PrintWriter pw) throws IOException{ // test all combinations of 3 points for collinearity 
		
		for (int i = 0; i < points.length; i++){
			for (int j = i+1; j < points.length; j++){
				for (int k = j+1; k < points.length; k++){ // O(n^3), right?
					if (collinearTest(points[i], points[j], points[k]) == true){
						lines.add(new Line(points[i], points[j], points[k]));
						//System.out.println(points[i] + " " + points[j] + " " + points[k]);
					}
				}
			}
		}
		
		for (int i = 0; i < lines.size(); i++){
			for (int j = i+1; j < lines.size(); j++){
				if (lines.get(i).getp1().slopeTo(lines.get(i).getp3()) != lines.get(j).getp1().slopeTo(lines.get(j).getp3())){ // if 2 lines slopes are explicitly different
					pointSimilarity(lines.get(i), lines.get(j)); // check for an intersection point
						
				}
			}
		}
		//System.out.println(solutionPoints.toString());
		outputSolutions(pw); // start printing to output file
		solutionPoints.clear(); // clear solutions list for next case
		
	}
	
	public void pointSimilarity(Line l1, Line l2){ // extremely poor implementation, should've used Line2D in hindsight, but this works too
		
		if (l1.getp1() == l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && // if just 1 point is shared among lines
			l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() && // it is an intersection
			l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp1());
			
		}else if (l1.getp1() != l2.getp1() && l1.getp1() == l2.getp2() && l1.getp1() != l2.getp3() && 
				  l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				  l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp1());
			
		}else if (l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() == l2.getp3() && 
				l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp1());
			
		}else if(l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
				l1.getp2() == l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp2());
			
		}else if(l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
				l1.getp2() != l2.getp1() && l1.getp2() == l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp2());
			
		}else if((l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
			l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() == l2.getp3() &&
			l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3())){
			
			solutionPoints.add(l1.getp2());
			
		}else if(l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
				l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() == l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp3());
			
		}else if(l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
				l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() != l2.getp1() && l1.getp3() == l2.getp2() && l1.getp3() != l2.getp3()){
			
			solutionPoints.add(l1.getp3());
			
		}else if (l1.getp1() != l2.getp1() && l1.getp1() != l2.getp2() && l1.getp1() != l2.getp3() && 
				l1.getp2() != l2.getp1() && l1.getp2() != l2.getp2() && l1.getp2() != l2.getp3() &&
				l1.getp3() != l2.getp1() && l1.getp3() != l2.getp2() && l1.getp3() == l2.getp3()){
			
			solutionPoints.add(l1.getp3());
			
		}
	}

	public boolean collinearTest(Point i, Point j, Point k){ // take in 3 points and see if they lie on the same line
		return (i.getY() - j.getY()) * (i.getX() - k.getX()) == (i.getY() - k.getY()) * (i.getX() - j.getX());
	}
	
	public void outputSolutions(PrintWriter pw){ // print to file
			
			numIntersections = solutionPoints.size();
			
			if (numIntersections == 0){ // if there are no intersections
				pw.println("0");
				pw.flush();
			}else{
				pw.println(numIntersections);
				for (Point s : solutionPoints){
					//System.out.println(s);
					pw.println(s.getX() + " " + s.getY());
					pw.flush();
				}
			}
			pw.println();
	}
	
	public void outputZero(PrintWriter pw){
		numIntersections = 0;
		
		pw.println("0");
		pw.flush();
		pw.println();
	}
}

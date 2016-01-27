import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Scanner;
import java.util.Stack;
import java.util.concurrent.TimeUnit;


public class BinaryTreeSum {
	
	public static void main(String[] args){
		
		long start = System.nanoTime(); // time test
		
		try {
			prepFile();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		long elapsedTime = System.nanoTime() - start;
		long durationInMs = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
		System.out.println("Total time elapsed: " + durationInMs + "ms");
		
	}
	
	private static void prepFile() throws IOException{
		
		Scanner sc = null; // allow scanner to be called outside of try catch
		PrintWriter out = null;
		//int desiredSum;
		
		
		try {
			out = new PrintWriter("C:\\hw1\\2015843489.txt");
			sc = new Scanner (new BufferedReader (new FileReader("C:\\hw1\\input.txt"))); // open file
			
			int desiredSum;
			
			sc.nextLine(); // skip first line of file (# of test cases)
			
			while (sc.hasNextLine()) { // avoid OOB error
				if (sc.hasNextInt()){
					desiredSum = sc.nextInt(); // number we are trying to achieve by summing root-to-leaf paths
					System.out.println(desiredSum);
					String answer = trailBlazer(sc, desiredSum);
					System.out.println(answer);
					out.println(answer);
					out.flush();
					System.out.println();
				}				
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			if (sc != null) sc.close();
			if (out != null) out.close();
		}
	}
	
	private static String trailBlazer(Scanner sc, int desiredSum){
		
		Scanner in = null;
		String currentLine;
		String solution;
		Stack<Integer> st = new Stack<Integer>();
		StringBuilder sb = new StringBuilder();
		int direction = 0;
		
		currentLine = sc.nextLine().replaceAll("\\s",""); // remove all whitespace 
		System.out.println(currentLine);
		
		if (currentLine.equals("()")){ // empty tree
			sb.append("-1");
			solution = sb.toString();
			return solution;
		}
		
		in = new Scanner (currentLine);
		in.useDelimiter("[^0-9]+");
		
		try {
		
			for (int i = 0; i < currentLine.length(); i++) { // iterate through tree, with cases for determining path
				if(st.isEmpty() && i > 0){
					sb.append("-1");
					solution = sb.toString();
					return solution;
				}
				if (currentLine.charAt(i) == '(' && Character.isDigit(currentLine.charAt(i+1))){
					st.push(in.nextInt());
					sb.append(direction);
					if (Character.isDigit(currentLine.charAt(i+3))){
						direction = 0;
					}
				} else if (currentLine.substring(i,i+4).equals("()()")){
					if (compare(desiredSum, st) == true){
						solution = sb.toString();
						return solution;
						
					}
				} else if (currentLine.substring(i, i+2).equals("))")){
					st.pop();
					sb.setLength(sb.length() - 1);
					direction = switchDirection(direction);
				} else if (currentLine.substring(i,i+2).equals(")(")){
					direction = switchDirection(direction);
				} else if (currentLine.substring(i,i+3).equals("()(")){
					direction = switchDirection(direction);
				}
			}
			System.out.println(st);
			System.out.println(sb);
		}catch (IndexOutOfBoundsException e){
			sb.setLength(0);
			sb.append("-1");
			solution = sb.toString();
			return solution;
		} finally {
			if (in != null) in.close();
		}
		return null;
	}
	
	private static boolean compare(int desiredSum, Stack<Integer> st) { //compare root-to-leaf path to desired sum
		@SuppressWarnings("unchecked")
		Stack<Integer> m_st = (Stack<Integer>) st.clone();
		int sum = 0;
		for (int i = 0; i < st.size(); i++){
			sum = sum + m_st.pop();
		}
		if (sum == desiredSum){
			//System.out.println("true");
			return true;
		}else {
			//System.out.println("False");
			return false;
		}
	}
	
	private static int switchDirection(int direction){ // switch between left and right for building string path
		if (direction == 0){
			return 1;
		}else {
			return 0;
		}
	}
}
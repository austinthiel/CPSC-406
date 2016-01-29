import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		long start = System.nanoTime(); // system timer start
		
		IOHandler io = new IOHandler();
		
		io.openFileIO();
		io.caseManager();
		//io.computeSimplex();
		io.closeFileIO();
		
		long elapsedTime = System.nanoTime() - start; // subtract new current time from start time to obtain time elapsed in nanoseconds
		long durationInMs = TimeUnit.NANOSECONDS.toMillis(elapsedTime); // convert nanoseconds to milliseconds
		System.out.println("Total time elapsed: " + durationInMs + "ms"); // print with formatting
	}
}

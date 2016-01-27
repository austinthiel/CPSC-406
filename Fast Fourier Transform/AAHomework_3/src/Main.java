import java.io.FileNotFoundException;
import java.util.concurrent.TimeUnit;


public class Main {
	public static void main(String[] args) throws FileNotFoundException{
		
		long start = System.nanoTime(); // system timer start
		
		IOHandler io = new IOHandler(); // create an IOHandler object for use in this class
		
		io.openFile(); // initialize scanner and printwriter for the file
		io.fileManager(io.getScanner()); // do all the dirty work
		io.closeScannerAndPrintWriter(); // close up shop
		
		long elapsedTime = System.nanoTime() - start; // subtract new current time from start time to obtain time elapsed in nanoseconds
		long durationInMs = TimeUnit.NANOSECONDS.toMillis(elapsedTime); // convert nanoseconds to milliseconds
		System.out.println("Total time elapsed: " + durationInMs + "ms"); // print with formatting
	}
}

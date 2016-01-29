import java.io.IOException;
import java.util.concurrent.TimeUnit;

public class Start {
	
	public static void main(String[] args){
		
		long start = System.nanoTime(); // time test
		
		FileHandler fh = new FileHandler();
		
		try {
			fh.openFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
				
	long elapsedTime = System.nanoTime() - start;
	long durationInMs = TimeUnit.NANOSECONDS.toMillis(elapsedTime);
	System.out.println("Total time elapsed: " + durationInMs + "ms");
		
	}
}

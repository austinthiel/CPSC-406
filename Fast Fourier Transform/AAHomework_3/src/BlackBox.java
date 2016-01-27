
public class BlackBox{
	
	private final double pi = 3.141592; // exact rounding of pi per assignment specifications
	
	protected Complex[] computeFFT(Complex[] data){
		
		int n = data.length; // length of array
		
		if(n == 1){ return new Complex[] { data[0]}; } // base recursive case, take every individual element and start stacking back to the root
		
		if (n%2 != 0) { n++; } // if the length of the array n is odd, make it even
		
		Complex[] even  = new Complex[n/2];
		for(int i = 0; i < n/2; i++){
			even[i] = data[2*i]; // grab all the even indices of the array and store them in a different array
		}
		Complex[] evenRec = computeFFT(even); // recursively compute the array of evens
		
		Complex[] odd = even; // re-use the first array because we can
		for(int i = 0; i < n/2; i++){ 
			odd[i] = data[2*i + 1]; // grab the odd indices and store them
		}
		Complex[] oddRec = computeFFT(odd); // recursively compute
		
		Complex[] solutionSet = new Complex[n]; // time to load the individuals back up
		for(int i = 0; i < n/2; i++){
			double ith = 2 * i * pi / n; // computing for omega
			Complex w = new Complex(Math.cos(ith), Math.sin(ith));
			solutionSet[i] = evenRec[i].plus((w).times(oddRec[i])); // compute the Complex solutions
			solutionSet[i + n/2] = evenRec[i].minus(w.times(oddRec[i])); // and add them to the new array
		}			
		return solutionSet; // return the array of Complex solutions
	}
}
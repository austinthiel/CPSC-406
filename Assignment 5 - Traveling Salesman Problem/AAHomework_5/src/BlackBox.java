import java.util.ArrayList;
import java.util.Scanner;


public class BlackBox {
	
	private int[][] ar;
	private int[][] redAr;
	private ArrayList<Integer> rowOrder;
	private ArrayList<Integer> colOrder;
	private StringBuilder sb = new StringBuilder();
	private int minTravel = 0;
	
	protected void initArray(int n){
		ar = new int[n][n];
		initPosTracker(n);
	}
	
	protected void initPosTracker(int n){
		rowOrder = new ArrayList<Integer>();
		colOrder = new ArrayList<Integer>();
		for(int i = 0; i < n; i++){
			rowOrder.add(i);
			colOrder.add(i);
		}
	}	
	
	protected void compute(){
		cloneArray(ar);
		while(redAr.length > 0){
			computePenalties(redAr);
		}
		System.out.println(minTravel);
	}
	
	protected void computePenalties(int[][] A){		

		int maxPenalty = 0;
		int rowPenalty = -1;
		int colPenalty = -1;
		int minX = Integer.MAX_VALUE;
		int minY = Integer.MAX_VALUE;
		int currPenalty = -1;
		
		subtractRowMinimum(A);
		subtractColMinimum(A);
		
		for(int i = 0; i < A.length; i++){
			for(int j = 0; j < A.length; j++){
				if(A[i][j] == 0){
					for(int k = 0; k < A.length; k++){
						if(A[i][k] != -1 && A[i][k] < minX && k != j){
							minX = A[i][k];
						}
						if(A[k][j] != -1 && A[k][j] < minY && k != i){
							minY = A[k][j];
						}
					}
					if(minX == Integer.MAX_VALUE){
						minX = -1;
					}
					if(minY == Integer.MAX_VALUE){
						minY = -1;
					}
					currPenalty = minX + minY;
					if(currPenalty > maxPenalty){ // save 2D location of where the max penalty is
						maxPenalty = currPenalty;
						rowPenalty = i;
						colPenalty = j;
					}
					minX = Integer.MAX_VALUE;
					minY = Integer.MAX_VALUE;
				}
			}	
		}
		if(rowPenalty == -1){
			rowPenalty = 0;
		}
		if(colPenalty == -1){
			colPenalty = 0;
		}
		sb.append(rowOrder.get(rowPenalty) + " -> " + colOrder.get(colPenalty) + "\n");		
		minTravel += ar[rowOrder.get(rowPenalty)][colOrder.get(colPenalty)];
	
		if(rowOrder.contains(colOrder.get(colPenalty)) && colOrder.contains(rowOrder.get(rowPenalty))){
			A[colPenalty][rowPenalty] = -1;
		}

		
		reduceMatrix(A, rowPenalty, colPenalty);
	}
	
	protected void reduceMatrix(int[][] A, int X, int Y){
		redAr = new int[A.length-1][A.length-1];
		int p = 0;
		for(int i = 0; i < A.length; i++){
			if(i == X){
				continue;
			}
			int q = 0;
			for(int j = 0; j < A.length; j++){
				if(j == Y){
					continue;
				}
				redAr[p][q] = A[i][j];
				q++;
			}
			p++;
		}
		rowOrder.remove(X);
		colOrder.remove(Y);
	}
	
	protected void loadArray(Scanner sc){
		for(int row = 0; row < ar.length; row++){
			for(int col = 0; col < ar.length; col++){
				ar[row][col] = sc.nextInt();
			}
		}
	}
	
	protected void subtractRowMinimum(int [][] A){
		for(int i = 0; i < A.length; i++){
			int min = Integer.MAX_VALUE;
			for(int j = 0; j < A.length; j++){
				if(A[i][j] < min && A[i][j] != -1){
					min = A[i][j];
				}
			}
			for(int j = 0; j < A.length; j++){
				if(A[i][j] != -1){
					A[i][j] -= min;
				}
			}
		}
	}
	
	protected void subtractColMinimum(int [][] A){
		for(int i = 0; i < A.length; i++){
			int min = Integer.MAX_VALUE;
			for(int j = 0; j < A.length; j++){
				if(A[j][i] < min && A[j][i] != -1){
					min = A[j][i];
				}
			}
			for(int j = 0; j < A.length; j++){
				if(A[j][i] != -1){
					A[j][i] -= min;
				}
			}
		}
	}
	
	protected void cloneArray(int[][] A){
		redAr = new int[A.length][A.length];
		for(int i = 0; i < A.length; i++){
			for(int j = 0; j < A.length; j++){
				redAr[i][j] = A[i][j];
			}
		}
	}
	
	protected void printArray(int[][] A){
		for(int i = 0; i < A.length; i++){
			for(int j = 0; j < A.length; j++){
				System.out.print(A[i][j] + " ");
			}
			System.out.println();
		}
	}
}

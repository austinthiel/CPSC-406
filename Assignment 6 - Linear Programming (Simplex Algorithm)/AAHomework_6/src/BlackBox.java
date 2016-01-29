import java.text.DecimalFormat;
import java.util.ArrayList;


public class BlackBox{
	
	ArrayList<Double> ZjList = new ArrayList<Double>();
	ArrayList<Double> ZjMinusCjList = new ArrayList<Double>();
	StringBuilder sb;
	
	DecimalFormat df = new DecimalFormat("#.##");
	
	protected StringBuilder compute(ArrayList<ArrayList<Double>> conList, ArrayList<Double> objList, int numVars, int numSlackVars){	
		sb = new StringBuilder();
		//System.out.println("objList: " + objList);
		
		getZj(conList, objList);
		getZjMinusCj(objList, ZjList);
		
		while(ZjCjTest(ZjMinusCjList) != true){
			getZj(conList, objList);
			getZjMinusCj(objList, ZjList);
			
			//System.out.println("\nZj List: " + ZjList);
			//System.out.println("Cj (obj)List: " + objList);
			//System.out.println("ZjMinusCjList: " + ZjMinusCjList + "\n");
			
			int enterPos = getEnteringVar(ZjMinusCjList, numVars, numSlackVars);
			int leavePos = getLeavingVar(conList, enterPos);
			//System.out.println(leavePos);
			if(leavePos == -1){
				sb = new StringBuilder();
				sb.append("unbounded");
				return sb;
			}
			
			//System.out.println("EnterPos: " + enterPos);
			//System.out.println("LeavePos: " + leavePos);
			
			Double pivot = conList.get(leavePos).get(enterPos);
			//System.out.println("\nPivot: " + pivot + " at (" + leavePos + "," + enterPos + ")");
			
			if(pivot != 1){
				//System.out.println("NOT UNITY REDUCED");
				unityReduction(leavePos, pivot, conList);
				//printList(conList);
			}
			
			rowOps(leavePos, enterPos, conList);
			updateCb(conList, objList, leavePos, enterPos);
			//System.out.println(objList);
			//printList(conList);
			//System.out.println(ZjMinusCjList);
			
			printCurrAnswer(ZjMinusCjList.get(ZjMinusCjList.size()-1));
		}
		//System.out.println(sb);
		return sb; // unbounded
	}
	
	protected void printCurrAnswer(double currSol){
		sb.append(df.format(currSol) + " ");
	}
	
	protected void getZj(ArrayList<ArrayList<Double>> conList, ArrayList<Double> objList){
		ZjList.clear();
		double total = -1;
		
		for(int i = 1; i < conList.get(0).size(); i++){
			total = 0;
			for(int temp = 0; temp < conList.size(); temp++){
				//System.out.println("total += " + conList.get(temp).get(0) + " * " + conList.get(temp).get(i));
				total += conList.get(temp).get(0) * conList.get(temp).get(i);
			}			
		//System.out.println(total);
		ZjList.add(total);
		}
	}
	
	protected void getZjMinusCj(ArrayList<Double> objList, ArrayList<Double> zjList2){
		ZjMinusCjList.clear();
		for(int i = 0; i < zjList2.size(); i++){
			//System.out.println(ZjList.get(i) - objList.get(i));
			ZjMinusCjList.add((double) (zjList2.get(i) - objList.get(i)));
		}
	}
	
	protected boolean ZjCjTest(ArrayList<Double> ZjMinusCjList){
		for(int i = 0; i < ZjMinusCjList.size(); i++){
			if(ZjMinusCjList.get(i) < 0){
				return false;
			}
		}
		return true;
	}
	
	protected void unityReduction(int row, Double pivot, ArrayList<ArrayList<Double>> conList){
		for(int i = 1; i < conList.get(row).size(); i++){
			conList.get(row).set(i, conList.get(row).get(i)/pivot);
		}
	}
	
	protected void rowOps(int row, int col, ArrayList<ArrayList<Double>> conList){
		for(int i = 0; i < conList.size(); i++){
			if(i == row){
				continue;
			}else{
				int tempCol = col;
				Double currNum = conList.get(i).get(tempCol);
				for(int k = 1; k < conList.get(i).size(); k++){
					Double additiveNum = conList.get(i).get(k);
					Double pivotNum = conList.get(row).get(k);
					double tempNum = -(pivotNum * currNum) + additiveNum;
					//System.out.println("-(" + pivotNum + " * " + currNum + ") + " + additiveNum + " = " + tempNum);
					//System.out.println(currNum);
					conList.get(i).set(k, tempNum);
				}
			}
			
		}
		//System.out.println("\n==========ROW REDUCTION==========");
		//printList(conList);
		//System.out.println("=================================");
	}
	
	protected void updateCb(ArrayList<ArrayList<Double>> conList, ArrayList<Double> objList, int row, int col){
		conList.get(row).set(0, (double)objList.get(col-1));
		//System.out.println("\n============CB UPDATE============");
		//printList(conList);
		//System.out.println("=================================");
	}
	
	protected int getLeavingVar(ArrayList<ArrayList<Double>> conList, int enterPos) {
		int pos = -1;
		Double min = Double.MAX_VALUE;
		for(int i = 0; i < conList.size(); i++){
			ArrayList<Double> list = conList.get(i);
			Double numerator = list.get(list.size()-1); // RHS
			Double denominator = list.get(enterPos); // 
			Double div = numerator/denominator;
			//System.out.println(numerator + " / " + denominator + " = " + numerator/denominator);
			if(div == -0.0 || Double.isInfinite(div)){
				div = -1.0;
			}
			if(div < min && div >= 0.0){
				//System.out.println("in");
				min = div;
				pos = i;
			}
		}
		//System.out.println(pos);
		return pos;
	}

	protected int getEnteringVar(ArrayList<Double> zjMinusCjList2, int numVars, int numSlackVars){
		int pos = 0;
		Double min = Double.MAX_VALUE;
		for(int i = 0; i < numVars + numSlackVars; i++){ // Z col not included
			if(zjMinusCjList2.get(i) < min){
				min = zjMinusCjList2.get(i);
				pos = zjMinusCjList2.indexOf(min) + 1; // Offset objList by 1
				//System.out.println(pos);
			}
		}
		return pos;
	}
	
	protected void printList(ArrayList<ArrayList<Double>> conList){
		for(int i = 0; i < conList.size(); i++){
			System.out.println(conList.get(i));
		}
	}
}

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public class BlackBox {
	
	List<List<Integer>> orig; // 2d arraylist for each case
	List<List<Integer>> vList; // clone of the original arraylist
	int numCuts;
	
	public void initList(int numVertices){
		orig = new ArrayList<List<Integer>>();
		
		for(int i = 0; i < numVertices; i++){
			List<Integer> node = new ArrayList<Integer>(i);
			orig.add(node);
		}
	}
	
	public void loadEdge(int v1, int v2){ // undirected graph, so edges point both ways
		orig.get(v1).add(v2);
		orig.get(v2).add(v1);
	}
	
	public int minCut(int numVertices, int numEdges){
		List<Integer> subList;
		numCuts = 999999;
		int numIterations = numVertices * numEdges; // this number of iterations is arbitrarily chosen and could be made more efficient
		for(int p = 0; p < numIterations; p++){
			
			vList = new ArrayList<List<Integer>>(clone(orig)); // use the cloned arraylist for computing the mincut
			
			for(int k = numVertices; k > 2; k--){ // continue merging nodes until there are only 2 left
				
				int n1 = -1;
				while( n1 == -1 || vList.get(n1).isEmpty()) // make sure the random node isn't already emptied out (previously chosen)
					n1 = ThreadLocalRandom.current().nextInt(0, vList.size()); // choose a node
				
				int n2 = vList.get(n1).get((ThreadLocalRandom.current().nextInt(0, vList.get(n1).size()))); // choose a node connecting to n1 for merging
				
				vList.get(n1).addAll(vList.get(n2)); // add everything from n2 into n1
	
				removeSelfLoops(n1, n2); // delete cycles
	
				subList = vList.get(n2);
				subList.clear(); // empty out n2
	
				for(int i = 0; i < vList.size(); i++){ // pass through 2d array to change pointers to newly merged node
					subList = vList.get(i);
					for(int j = 0; j < vList.get(i).size(); j++){
						if(subList.get(j) == n2){
							subList.set(j, n1);
						}
					}
				}
			}
			for(int i = 0; i < vList.size(); i++){ // find a non-empty node and count the number of remaining connections (aka the mincut needed)
				subList = vList.get(i);
				if(!subList.isEmpty()){
					int tempNumCuts = subList.size();
					if(tempNumCuts < numCuts){
						numCuts = tempNumCuts;
						break;
					}
				}
			}
		}
		return numCuts;
	}
	
	public void removeSelfLoops(int n1, int n2){ // when 2 connecting nodes merge, they create cycles, which are unnecessary
		vList.get(n1).removeAll(Collections.singleton(n1));
		vList.get(n1).removeAll(Collections.singleton(n2));
	}
	
	public List<List<Integer>> getList(){
		return vList;
	}
	
	public void printList(){
		System.out.println(vList);
	}
	
	public List<List<Integer>> clone(List<List<Integer>> orig){ // essentially used to "pass-by-value" so mincut can run multiple times
		List<List<Integer>> clone = new ArrayList<List<Integer>>(orig.size());
		for (int i = 0; i < orig.size(); i++){
			List<Integer> line = orig.get(i);
			clone.add(new ArrayList<Integer>(line.size()));
			for(int j = 0; j < line.size(); j++){
				clone.get(i).add(line.get(j));
			}
		}
		return clone;
	}
}

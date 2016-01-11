package competition;

import java.util.ArrayList;

public class Placement implements Comparable<Placement>{
	private int placement;
	private ArrayList<Result> results = new ArrayList<>();
	
	public Placement(int placement, ArrayList<Result> results){
		this.placement = placement;
		this.results = results;
	}
	public Placement(int placement,Result r){
		this.placement = placement;
		this.results.add(r);
	}
	public boolean removeResult(Result r){
		return results.remove(r);
	}
	public String[] toPrint() {
		String[] output = new String[results.size()];
		for(int i = 0; i<results.size();i++){
			output[i] = results.get(i).toPrint();
		}
		return output;
	}
	@Override
	public int compareTo(Placement placement) {
		return this.getPlacement()-placement.getPlacement();
	}
	//get and set
	public void addResult(Result r){
		results.add(r);
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public int getPlacement(){
		return placement;
	}
}

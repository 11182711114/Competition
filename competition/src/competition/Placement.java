package competition;

import java.util.ArrayList;

public class Placement {
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

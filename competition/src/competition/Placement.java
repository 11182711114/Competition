package competition;

import java.util.ArrayList;

public class Placement implements Comparable<Placement>{
	private int placement;
	private ArrayList<Result> results = new ArrayList<>();
	
	public Placement(int placement){
		this.placement = placement;
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
		if(!results.contains(r)){
			results.add(r);
		}
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public int getPlacement(){
		return placement;
	}
	public String toString(){
		String output = placement + " - ";
		for(Result r : results){
			output+="%"+r;
		}
		return "{"+output+"}";
	}
}

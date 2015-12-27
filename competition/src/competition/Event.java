package competition;

import java.util.ArrayList;

public class Event {
	private ArrayList<Result> results = new ArrayList<Result>();
	
	private String eventName;
	private int attempts;
	private boolean biggerBetter;
	
	public Event(String eName, int tries, boolean largerBetter){
		 eventName = eName;
		 attempts = tries;
		 biggerBetter = largerBetter;
	}
	public String getName(){
		return eventName;		
	}
	public int getTries(){
		return attempts;		
	}
	public boolean isBiggerBetter(){
		return biggerBetter;
	}
	public boolean getComp(){
		return biggerBetter;		
	}
	public void addResult(Result r){
		results.add(r);
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public String toDb(){
		return "|e|"+eventName+"|/e|" + "|a|"+attempts+"|/a|" + "|b|"+biggerBetter+"|/b|";
	}
}

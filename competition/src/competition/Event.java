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
	public boolean getBiggerBetter(){
		return biggerBetter;
	}
	public void setName(String s){
		eventName = s;
	}
	public void setTries(int i){
		attempts = i;		
	}
	public void setIsBiggerBetter(boolean iBB){
		biggerBetter = iBB;
	}
	public void addResult(Result r){
		results.add(r);
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public boolean checkAllowedMoreAttempts(Participant p){
		if(checkNumberAttempts(p)<attempts){
			return true;
		}
		return false;		
	}
	public int checkNumberAttempts(Participant p){
		int numberAttempts = 0;
		for(Result r : results){
			if(r.getParticipant()==p){
				numberAttempts++;
			}
		}
		return numberAttempts;
	}
	public ArrayList<Result> removeResultsByParticipant(Participant p){
		ArrayList<Result> outResults = new ArrayList<>();
		for(Result r : results){
			if(r.getParticipant()==p){
				outResults.add(r);
			}
		}
		if(results.removeAll(outResults)){
			return outResults;
		}
		return null;
	}
	public String toDb(){
		return "|e|"+eventName+"|/e|" + "|a|"+attempts+"|/a|" + "|b|"+biggerBetter+"|/b|";
	}
	public String[] resultTags(){
		String[] str = {"|r|","|/r|" , "|pID|","|/pID|"};
		return str;
	}
}
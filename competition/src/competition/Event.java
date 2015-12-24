package competition;

public class Event {
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

}

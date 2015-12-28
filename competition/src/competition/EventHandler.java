package competition;

import java.util.ArrayList;

public class EventHandler {
	private ArrayList<Event> events = new ArrayList<Event>();
	private Competition comp;
	
	public EventHandler(Competition c){
		comp = c;
	}
	public ArrayList<Event> getAllEvents(){
		return events;
	}
	public void addEvent(){
		String eventName;
		boolean incorrectName = false;
		
		do{
			eventName = comp.normalize(comp.inputString("Event name:"),1);
			if(eventName==null){
				incorrectName = true;
				System.out.println("Error 01:Names cannot be empty!");
			}
		}while(incorrectName);
		
		int attempts;
		boolean tooLowAttempts = false;
		do{
			attempts = comp.inputNumber("Attempts allowed:").intValue();
			if(attempts<1){
				tooLowAttempts = true;
				System.out.println("Error 02: Attempts value too low, allowed: 1 or higher");
			}
		}while(tooLowAttempts);
		
		String biggerBetter;
		boolean incorrectInput = false;
		do{
			biggerBetter = comp.normalize(comp.inputString("Bigger better? (Y/N):"),2);
			if(!biggerBetter.equals("y") && !biggerBetter.equals("n") && !biggerBetter.equals("yes") && !biggerBetter.equals("no")){
				incorrectInput = true;
				System.out.println("Error 03: Incorrect input, allowed sepparated by \",\": y,n,yes,no");
			}
		}while(incorrectInput);
		
		boolean isBiggerBetter = false;
		if(biggerBetter.equals("y") || biggerBetter.equals("yes")){
			isBiggerBetter = true;
		}
		
		Event thisEvent = new Event(eventName,attempts,isBiggerBetter);
		boolean alreadyExists = false;
		String eName = thisEvent.getName();
		
		for(Event e: events){					
			if(e.getName().equals(eName)){
				alreadyExists = true;
				System.out.println("Error 04:"+eName+" has already been added");
			}						
		}
		if(!alreadyExists){
			events.add(thisEvent);
			System.out.println(thisEvent.getName()+" added");
		}
	}
	public void addEvent(String name, int tries, boolean iBB){
		String eventName = name;
		int attempts = tries;
		boolean isBiggerBetter = iBB;
		
		Event thisEvent = new Event(eventName,attempts,isBiggerBetter);
		boolean alreadyExists = false;
		String eName = thisEvent.getName();
		
		for(Event e: events){					
			if(e.getName().equals(eName)){
				alreadyExists = true;
			}						
		}
		if(!alreadyExists){
			events.add(thisEvent);
		}
	}
	public Event getEventByName(String eName){
		for(Event e : events){
			if(e.getName().equals(eName)){
				return e;
			}
		}
		return null;
	}
	public boolean reinitialize(){
		events.clear();
		if(events.isEmpty()){
			return true;
		}
		return false;
	}
}

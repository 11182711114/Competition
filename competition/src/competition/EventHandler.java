package competition;

import java.util.ArrayList;
import java.util.Collections;

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
		int attempts = 0;
		boolean isBiggerBetter = false;
		
		do{
			eventName = comp.normalize(comp.inputString("Event name:"),1);
			if(eventName==null){
				System.out.println("Names cannot be empty");
			}
		}while(eventName==null);
		
		if(doesEventExist(eventName)){
			System.out.println(eventName + " already exists");
		}
		else{
			boolean tooLowAttempts = false;
			do{
				double attemptsTemp = comp.inputNumber("Attempts allowed:");
				if(!Double.isNaN(attemptsTemp)){
					attempts = (int) attemptsTemp;
					if(attempts<1){
						tooLowAttempts = true;
						System.out.println("Error 02: Attempts value too low, allowed: 1 or higher");
					}
					else{
						tooLowAttempts = false;
					}
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
				else{
					incorrectInput = false;	
				}
			}while(incorrectInput);
			
			if(biggerBetter.equals("y") || biggerBetter.equals("yes")){
				isBiggerBetter = true;
			}
			
			if(attempts!=0){
				Event thisEvent = new Event(eventName,attempts,isBiggerBetter);
				events.add(thisEvent);
				System.out.println(thisEvent.getName()+" added");
			}
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
	public boolean doesEventExist(String eName){
		if(getEventByName(eName)!=null){
			return true;
		}
		return false;
	}
	public Event getEventByName(String eName){
		for(Event e : events){
			if(e.getName().equalsIgnoreCase(eName)){
				return e;
			}
		}
		return null;
	}
	public boolean getBiggerBetterForEventByName(String eName){
		return getEventByName(eName).getBiggerBetter();
	}
	public void printResultsByParticipant(Participant p){
		boolean hasResults = false;
		for(Event e : events){
			if(e.checkNumberAttempts(p)>0){
				hasResults = true;
				System.out.print("Results for "+ p.getFullName() +" in "+ e.getName() +": ");
				double[] results = getFormattedResultForParticipantByEvent(p,e);
				for(int i = 0; i<results.length;i++){
					if(i+1>=results.length){
						System.out.println(results[i]);
					}
					else{
						System.out.print(results[i]+", ");
					}
				}
			}
		}
		if(!hasResults){
			System.out.println(p + " has no registered results");
		}
	}
	public void printResultByEvent(String eventName){
		ArrayList<Placement> placements = getEventByName(eventName).getPlacements();
		Collections.sort(placements);
		for(Placement place : placements){
			String[] output = place.toPrint();
			for(String s : output){
				System.out.println(s);
			}
		}
	}
	public void printResultByParticipant(){
		double temp = comp.inputNumber("Participant ID:");
		if(!Double.isNaN(temp)){
			int id = (int) temp;
			if(comp.doesParticipantExist(id)){
				printResultsByParticipant(comp.getParticipantByID(id));
			}
			else{
				System.out.println("No participant with ID " + id);
			}
		}
	}
	private double[] getFormattedResultForParticipantByEvent(Participant p, Event e){
		ArrayList<Result> results = new ArrayList<>();
		ArrayList<Result> tempResults = e.getResults();
		for(Result r : tempResults){
			if(r.getParticipant()==p){
				results.add(r);
			}
		}
		double[] output = new double[results.size()];
		for(int i = 0; i<results.size();i++){
			output[i]=results.get(i).getResult();
		}
		return output;
	}
	public void addResult(){
		double tempID = comp.inputNumber("Participants ID:");
		if(!Double.isNaN(tempID)){
			int pID = (int) tempID;
			
			Participant p = comp.getParticipantByID(pID);
			if(p!=null){
				String eventName = comp.normalize(comp.inputString("Event name:"),1);
				Event e = getEventByName(eventName);
				if(e!=null){
					if(e.checkAllowedMoreAttempts(p)){
						double thisResultValue;
						boolean inproperValue = false;
						do{
							if(inproperValue){
								System.out.println("Error 09: Incorrect input, only results >0 accepted");						
							}					
							thisResultValue = comp.inputNumber("Result as decimal number:");
							if(!Double.isNaN(thisResultValue)){
								if(thisResultValue<0){
									inproperValue = true;
								}
								else{
									inproperValue = false;
								}
							}
						}while(inproperValue);
						Result r = new Result(p,e,thisResultValue);
						e.addResult(r);
						p.addResult(r);
						System.out.println(r + " has been added");
						e.updatePlacement();
					}
					else{
						System.out.println("Too many attempts!");
					}
				}
				else{
					System.out.println("No such event");
				}
			}
			else{
				System.out.println("No such participant: "+pID);
			}
		}
	}
	public void removeResultsByParticipant(Participant p){
		for(Event e : events){
			e.removeResultsByParticipant(p);
			e.updatePlacement();
		}
	}
	public boolean reinitialize(){
		events.clear();
		if(events.isEmpty()){
			return true;
		}
		return false;
	}
}
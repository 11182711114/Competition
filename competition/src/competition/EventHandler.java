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
			if(e.getName().equalsIgnoreCase(eName)){
				return e;
			}
		}
		return null;
	}
	public void printResults(ArrayList<Result> results){
		ArrayList<Result> sortedResults = sortResults(results);
		
		int placementIndex = 1;
		int skipNextNumbers = 0;
		boolean printNext = false;
		//if(biggerBetter){
			//iterate the sortedResults if a bigger number is better
			for(int i = 0; i<sortedResults.size();i++){
				//if this result is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
				if(1+i<sortedResults.size() && skipNextNumbers==0 && sortedResults.get(i).getResult()==sortedResults.get(i+1).getResult()){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName());
					skipNextNumbers++;
					printNext = true;
				}
				//if this result and the one before was equal print this with the same index and increase placementIndex
				else if(printNext){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName());
					printNext = false;
					placementIndex++;
				}
				//if skipNextNumber is >0 we increase placementIndex by 1 and decrease skipNextNumbers by 1
				else if(skipNextNumbers>0){				
					placementIndex++;
					skipNextNumbers--;
					i--;
				}			
				//if the result is not equal to the next we print it out at placementIndex
				else{
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName());
					placementIndex++;
				}		
			}
	}
	private ArrayList<Result> sortResults(ArrayList<Result> results){
//		ArrayList<Result> sortedResults = new ArrayList<>();
//		
//		boolean swapped = false;
//		do{
//			swapped = false;
//			for(int i = 1; i<results.size(); i++){				
//				if(biggerBetter && results.get(i).getResult()>results.get(i-1).getResult()){
//					Result tempResult = results.get(i);
//					sortedResults.add(i-1,tempResult);
//					swapped = true;
//				}
//				else if(!biggerBetter && results.get(i).getResult()<results.get(i-1).getResult()){
//					Result tempResult = results.get(i);
//					sortedResults.add(i-1,tempResult);
//					swapped = true;
//				}
//			}
//		}while(swapped);
//		return sortedResults;
		ArrayList<Result> sortedResults = results;
		Collections.sort(results);
		return sortedResults;
	}
	public ArrayList<Result> eventUniqueResults(String eName){
		ArrayList<Result> unsortedBestResults = new ArrayList<Result>();
		Event event = getEventByName(eName);
		
		//go through all participants and find their best result and then store it in the output array
		for(Participant thisParticipant : comp.getParticipants()){
			Result bestResult = null;
			
			for(Result thisResult : event.getResults()){
				//check if bigger result is better and if its the right person
				if(event.isBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
					if(bestResult!=null){
						if(thisResult.getResult()>bestResult.getResult()){
							bestResult = thisResult;
						}
					}
					else{
						bestResult = thisResult;
					}
				}
				//if smaller result is better and if its the right person
				else if(!event.isBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
					if(bestResult!=null){
						if(thisResult.getResult()<bestResult.getResult()){
							bestResult = thisResult;
						}
					}
					else{
						bestResult = thisResult;
					}
				}
			}
			if(bestResult!=null){
				unsortedBestResults.add(bestResult);
			}
		}
		return unsortedBestResults;
	}
	public boolean reinitialize(){
		events.clear();
		if(events.isEmpty()){
			return true;
		}
		return false;
	}
}

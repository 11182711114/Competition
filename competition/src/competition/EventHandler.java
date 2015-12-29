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
	public boolean getBiggerBetterForEventByName(String eName){
		return getEventByName(eName).getBiggerBetter();
	}
	public void printResultsWithPlacement(ArrayList<Result> results,boolean biggerBetter){
		ArrayList<Result> sortedResults = sortResults(results);
		
		int placementIndex = 1;
		int skipNextNumbers = 0;
		boolean printNext = false;
		if(biggerBetter){
		//iterate the sortedResults if a bigger number is better
			for(int i = 0; i<sortedResults.size();i++){
				//if this result is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
				if(1+i<sortedResults.size() && skipNextNumbers==0 && sortedResults.get(i).getResult()==sortedResults.get(i+1).getResult()){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
					skipNextNumbers++;
					printNext = true;
				}
				//if this result and the one before was equal print this with the same index and increase placementIndex
				else if(printNext){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
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
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
					placementIndex++;
				}		
			}
		}
		else{//if !biggerBetter we iterate through the list in reverse order and compare i to i-1 instead
			for(int i = sortedResults.size()-1; i>=0;i--){
				//if this result is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
				if(i-1>=0 && skipNextNumbers==0 && sortedResults.get(i).getResult()==sortedResults.get(i-1).getResult()){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
					skipNextNumbers++;
					printNext = true;
				}
				//if this result and the one before was equal print this with the same index and increase placementIndex
				else if(printNext){
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
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
					System.out.println(placementIndex + " " +sortedResults.get(i).getResult()+" "+ sortedResults.get(i).getParticipantName() +" "+ sortedResults.get(i).getTeamName());
					placementIndex++;
				}		
			}
		}
	}
	public void printResults(Participant p){
		for(Event e : events){
			System.out.print("Results for "+ p.getFullName() +" in "+ e.getName() +": ");
			double[] results = getFormattedResultForParticipantForEvent(p,e);
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
	public void printResultByEvent(String eventName){
		ArrayList<Result> resultToPrint = eventUniqueResults(eventName);
		System.out.println("Results for " + comp.normalize(eventName,1));
		printResultsWithPlacement(resultToPrint,getBiggerBetterForEventByName(eventName));
	}
	public void printResultByParticipant(){
		int id = comp.inputNumber("Participant ID:").intValue();
		if(comp.doesParticipantExist(id)){
			printResults(comp.getParticipantByID(id));
		}
		else{
			System.out.println("No participant with ID " + id);
		}
	}
	private ArrayList<Result> sortResults(ArrayList<Result> results){
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
				if(event.getBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
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
				else if(!event.getBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
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
	private double[] getFormattedResultForParticipantForEvent(Participant p, Event e){
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
		int pID = comp.inputNumber("Participants ID:").intValue();
		String eventName = comp.normalize(comp.inputString("Event name:"),1);
		boolean incorrectP = true;
		boolean incorrectE = true;
		
		for(Participant p : comp.getParticipants()){
			if(p.getID() == pID){
				incorrectP = false;
			}
		}
		if(incorrectP){
			System.out.println("Error 07: Incorrect participant ID given: "+pID);
		}
		Event thisEvent = null;
		int i = 0;
		for(Event e : getAllEvents()){
			if(e.getName().equalsIgnoreCase(eventName)){
				incorrectE = false;
				thisEvent = e;
				
				for(Result r : e.getResults()){
					if(r.getParticipant().getID() == pID){
						i++;
					}
				}
			}
		}
		if(incorrectE){
			System.out.println("Error 08: Incorrect event name given");
		}
		else if(!incorrectE && !incorrectP){
			double thisResult;
			int attempts = 0;
			do{
				if(attempts>0){
					System.out.println("Error 09: Incorrect input, only results >0 accepted");						
				}					
				thisResult = comp.inputNumber("Result as decimal number:");
			}while(thisResult<0);
			
			if(thisEvent!=null){
				if(!incorrectP && !incorrectE && thisEvent.getTries()>=i){
					Result newResult = new Result(comp.getParticipantByID(pID),thisEvent,thisResult);
					getEventByName(eventName).addResult(newResult);
					System.out.println("Result: "+ newResult+ " has been added");
				}
			}
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
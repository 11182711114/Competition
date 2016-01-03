package competition;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Comparator;

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
	public void printResults(Participant p){
		boolean hasResults = false;
		for(Event e : events){
			if(e.checkNumberAttempts(p)>0){
				hasResults = true;
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
		if(!hasResults){
			System.out.println(p + " has no registered results");
		}
	}
	public void printResultByEvent(String eventName){
		ArrayList<Result> results = sortResults(getEventByName(eventName).getUniqueResults());
		System.out.println("Results for " + comp.normalize(eventName,1));
		for(Result r : results){
			System.out.println(r.printResult());
		}
	}
	public void printResultByParticipant(){
		double temp = comp.inputNumber("Participant ID:");
		if(!Double.isNaN(temp)){
			int id = (int) temp;
			if(comp.doesParticipantExist(id)){
				printResults(comp.getParticipantByID(id));
			}
			else{
				System.out.println("No participant with ID " + id);
			}
		}
	}
	public String[][] getTeamMedals(){
		ArrayList<Team> teams = getTeams();
		String[][] medals = new String[teams.size()][4];
		for(int i = 0; i<teams.size(); i++){
			medals[i][0] = ""+0;
			medals[i][1] = ""+0;
			medals[i][2] = ""+0;
			medals[i][3] = teams.get(i).getName();
			for(Event e : events){
				e.updatePlacement();
				ArrayList<Result> eventMedals = e.getMedals();
				for(Result r : eventMedals){
					if(teams.get(i)==r.getTeam()){
						switch(r.getPlacement()){
						case 1:
							medals[i][0] =String.valueOf((Integer.parseInt(medals[i][0])+1));
							break;
						case 2:
							medals[i][1] =String.valueOf((Integer.parseInt(medals[i][1])+1));
							break;
						case 3:
							medals[i][2] =String.valueOf((Integer.parseInt(medals[i][2])+1));
							break;
						}
					}
				}
			}
		}
		return medals;
	}
	public ArrayList<Team> getTeams(){
		ArrayList<Participant> parts = comp.getParticipants();
		ArrayList<Team> teams = new ArrayList<>();
		for(Participant p : parts){
			if(!teams.contains(p.getTeam())){
				teams.add(p.getTeam());
			}
		}
		return teams;
	}
	public void printMedals(){
		String[][] output = sortMedals(getTeamMedals());

		for(String[] s : output){
			for(int i = 0; i<s.length;i++){
				System.out.print(s[i] + " ");
			}
			System.out.println();
		}
	}
	private String[][] sortMedals(String[][] medals){ // credit to "Boris the Spider" http://stackoverflow.com/questions/15452429/java-arrays-sort-2d-array for the base of the custom Comparator
		Comparator<String[]> compare = new Comparator<String[]>() {
			@Override
			public int compare(String[] o1, String[] o2) {
				int[] a1= {Integer.parseInt(o1[0]),Integer.parseInt(o1[1]),Integer.parseInt(o1[2])};
				int[] a2= {Integer.parseInt(o2[0]),Integer.parseInt(o2[1]),Integer.parseInt(o2[2])};
				int[] a3 = subtractArray(a1,a2);
				if(a3[0]>0){
					return -1;
				}
				else if(a3[0]<0){
					return 1;
				}
				else{
					if(a3[1]>0){
						return -1;
					}
					else if(a3[1]<0){
						return 1;
					}
					else{
						if(a3[2]>0){
							return -1;
						}
						else if(a3[2]<0){
							return 1;
						}
						else{
							if(o1[3].compareToIgnoreCase(o2[3])==1){
								return 1;
							}
							if(o1[3].compareToIgnoreCase(o2[3])==-1){
								return -1;
							}
							else{
								return 0;
							}
						}
					}
				}
			}
		};
		Arrays.sort(medals, compare);
		return medals;
	}
	private int[] subtractArray(int[] a1,int[] a2){
		int[] output = new int[a1.length];
		for(int i = 0;i<a1.length;i++){
			output[i] = a1[i]-a2[i];
		}
		return output;
	}
	private ArrayList<Result> sortResults(ArrayList<Result> results){
		ArrayList<Result> sortedResults = results;
		Collections.sort(results);
		return sortedResults;
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
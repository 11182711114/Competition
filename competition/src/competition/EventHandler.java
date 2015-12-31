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
					if(1+i<sortedResults.size() && sortedResults.get(i).getResult()==sortedResults.get(i+1).getResult()){
						printNext = true;
						skipNextNumbers++;
					}
					else{
						printNext = false;
						placementIndex++;
					}
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
					if(1+i<sortedResults.size()&& sortedResults.get(i).getResult()==sortedResults.get(i+1).getResult()){
						printNext=true;
						skipNextNumbers++;
					}
					else{
						printNext = false;
						placementIndex++;
					}
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
		ArrayList<Result> resultToPrint = getEventByName(eventName).uniqueResults();
		System.out.println("Results for " + comp.normalize(eventName,1));
		printResultsWithPlacement(resultToPrint,getBiggerBetterForEventByName(eventName));
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
	public void printMedals(){
		String[][] output = sortMedals(getMedals());

		for(String[] s : output){
			for(int i = 0; i<s.length;i++){
				System.out.print(s[i] + " ");
			}
			System.out.println();
		}
	}
	private String[][] getMedals(){
		ArrayList<Participant> parts = comp.getParticipants();
		ArrayList<Team> teams = new ArrayList<>();
		for(Participant p : parts){
			if(!teams.contains(p.getTeam())){
				teams.add(p.getTeam());
			}
		}
		String[][] str = new String[teams.size()][4];
		for(int i = 0; i<teams.size();i++){
			str[i][3] = teams.get(i).getName();
			int[] temp = getMedalsByTeam(teams.get(i));
			str[i][0]=""+temp[0];
			str[i][1]=""+temp[1];
			str[i][2]=""+temp[2];
		}
		return str;
	}
	private int[] getMedalsByTeam(Team t){
		int[] medals = new int[3];
		for(Event e : events){
			int[] temp = e.getMedalsByTeam(t);
			medals[0]+=temp[0];
			medals[1]+=temp[1];
			medals[2]+=temp[2];
		}
		return medals;		
	}
	private String[][] sortMedals(String[][] medals){ // credit to "Boris the Spider" http://stackoverflow.com/questions/15452429/java-arrays-sort-2d-array for the base of the custom Comparator
		Comparator<String[]> compare = new java.util.Comparator<String[]>() {
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
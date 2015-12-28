package competition;

import java.util.ArrayList;
import java.util.Scanner;
/*
 * TODO;
 * 	high priority
 * 		team()
 * 		participant()
 * 	med priority
 * 		clean code
 * 	low priority
 * 		read from file
 */
public class Competition {
	private Database db;
	private EventHandler eventHandler;
	
	private ArrayList<Participant> participants = new ArrayList<Participant>();
	private ArrayList<Team> teams = new ArrayList<Team>();
	private int nrOfRemoved = 0;
	
	public static void main(String[] args){
		Competition thisCompetition = new Competition();
		thisCompetition.initialize(thisCompetition);
		thisCompetition.run();	
		thisCompetition.exit();
	}
	//main functions
	private void initialize(Competition c){
		db = new Database(c);
		eventHandler = new EventHandler(c);
	}
	private void run(){
		menu();
		while(handleCommands(readCommand())){
		}
						
	}
	private void exit(){
		saveDb();
	}
	//database functions
	private void saveDb(){
		if(db.databaseSelected()){
			db.writeToFile(eventHandler.getAllEvents(), participants,teams);
		}
	}
	private void loadDb(){
		if(!db.databaseSelected()){
			db.setDatabase();
		}
		if(db.databaseExists()){
			ArrayList<Participant> parts = db.getParticipantsFromDb();
			if(!parts.isEmpty()){
				for(Participant p : parts){
					addParticipant(p.getName(),p.getFamilyName(),p.getTeam().getTeamName(),p.getID());
				}
			}
			ArrayList<Event> events = db.getEventsFromDb();
			if(!events.isEmpty()){
				for(Event e : events){
					eventHandler.addEvent(e.getName(),e.getTries(),e.isBiggerBetter());
					for(Result r : e.getResults()){
						eventHandler.getEventByName(e.getName()).addResult(r);
					}
				}
			}
		}
		else{
			System.out.println("New database, nothing to load");
		}
	}
	//run functions
	private String readCommand(){
		return normalize(inputString("Lyssnar:"),2);
	}
	private boolean handleCommands(String userInput){
		if(userInput.equals("load")){
			loadDb();
		}
		else if(userInput.equals("add event")){
			eventHandler.addEvent();
		}			
		else if(userInput.equals("add participant")){
			addParticipant();
		}
		else if(userInput.equals("remove participant")){
			removeParticipant();
		}
		else if(userInput.equals("list participants")){
			listParticipants();
		}
		else if(userInput.equals("add result")){
			addResult();								
		}
		else if(userInput.equals("participant")){
			//printParticipantResultsByEvent();
		}			
		else if(userInput.equals("teams")){				
		}
		else if(userInput.contains("message")){
			message(userInput);
		}
		else if(userInput.equals("reinitialize")){
			reinitialize();				
		}
		else if(userInput.equals("exit")){
			return false;
		}
		else{				
			boolean wrongInput = true;
			System.out.println(userInput);
			for(Event thisEvent : eventHandler.getAllEvents()){
				if(thisEvent.getName().equalsIgnoreCase(userInput)){
					wrongInput = false;
					//resultByEvent(userInput);
				}										
			}
			if(wrongInput){
				System.out.println("Error 00; wrong input given");
				menu();
			}
		}
		return true;	
	}	
	private void menu(){
		System.out.println("Availible options, non case-sensitive;");
		System.out.println("\"load\" - selects database to get information from and save to");
		System.out.println("\"add event\" - adds an event with given options");
		System.out.println("\"add participant\" - adds a participant and gives them an autogenerated ID");
		System.out.println("\"remove participant\" - removes a participant by ID");
		System.out.println("\"list participants\" - lists all participants");
		System.out.println("\"add result\" - adds a result for a participant, by ID, for a specific event, by name");
		System.out.println("$eventName - shows the result for given event");
	}
	//program functions
		//basic functions
	private void reinitialize(){
		//Reset nrOfRemoved, go through all of the ArrayLists and erase everything
		nrOfRemoved=0;
		
		eventHandler.reinitialize();
		participants.clear();
		teams.clear();
	}
	private void message(String s){
		//make new Message, start after "message "
		Message message = new Message(s.substring(8));
		message.printMessage();
	}
			//public functions
	public String inputString(String inputString){
		@SuppressWarnings("resource")
		Scanner tangentbord = new Scanner(System.in);
		System.out.print(inputString);
		return tangentbord.nextLine();		
	}
	public Double inputNumber(String inputNumber){		
		@SuppressWarnings("resource")
		Scanner tangentbord = new Scanner(System.in);
		System.out.print(inputNumber);
		Double dOutput = tangentbord.nextDouble();
		tangentbord.nextLine();
		return dOutput;
	}
	/*
	 *remove forbidden character, could use .trim() for whitespace only but
	 *	1. this is more powerful for future additions
	 *	2. because I didn't check the docs before doing it 
	 *	3. then got obsessed with making it work and justifiable
	 *
	 *it will remove all forbidden characters
	 *leave whitespace if it has characters next to it, e.g. "Boo FF"
	 *
	 *int c;
	 *0 = do nothing
	 *1 = force capitalization
	 *2 = force all lowercase
	*/
	public String normalize(String x, int c){
		String output = null;
		String[] forbidden = {" "};
		//iterate through the given string		
		for(int i=0; i<x.length(); i++){
			String l = x.substring(i,i+1);
			//iterate through the forbidden list
			for(int y = 0; y<forbidden.length;y++){
				if(!l.equalsIgnoreCase(forbidden[y])){
					//check if output is null
					
					if(output!=null){
						output=output+l;
					}
					else{
						output=l;						
						}					
				}
				//if it is a whitespace check if it has characters next to it
				if(l.equals(" ")){
					//check if it's the first or last character, if it is then it is incorrect anyways, if it's not we can check
					if(i>0 && i<x.length()-1){						
						if(!x.substring(i-1,i).equals(" ") && !x.substring(i+1,i+2).equals(" ")){
							output+=l;							
						}
					}															
				}
			}
		}
		//check if we trimmed the entire string
		if(output.isEmpty()){
			return null;
		}
		else{
			//first letter to uppercase	
			if(c == 1){
				return output.substring(0, 1).toUpperCase()+output.substring(1).toLowerCase();
			}
			else if(c == 2){
				return output.toLowerCase();
			}
			return output;			
		}		
	}
		//participant functions
	private void addParticipant(){
		String gName = normalize(inputString("Participants given name:"),1);
		String fName = normalize(inputString("Participants family name:"),1);
		String tName = normalize(inputString("Participants team name:"),1);
		
		if(gName != null && fName != null && tName != null){
			int ID = 100;
			Team team = makeTeam(tName);
			if(team!=null){			
			if(!participants.isEmpty()){
				ID = 1+participants.get(participants.size()-1).getID();
			}				
			participants.add(new Participant(gName,fName,team,ID));
			System.out.println(participants.get(ID-100-nrOfRemoved)+" added");}
		}
		else{
			System.out.println("Error 05; null value in add participant");
			}
	}
	private void addParticipant(String g, String f, String t, int id){
		String gName = g;
		String fName = f;
		String tName = t;
		int ID = id;
		
		if(gName != null && fName != null && tName != null){
			Team team = makeTeam(tName);
			if(team!=null){			
				participants.add(new Participant(gName,fName,team,ID));
			}
		}
	}
	private void removeParticipant(){
		int removedID = inputNumber("Participant ID to be removed:").intValue();
		
		int i = 0;				
		for(Participant p: participants){
			if(p.getID()==(removedID)){
				break;
			}
			i++;
		}
		if(i>=0 && i<participants.size()){
			System.out.println("Removing: "+ participants.get(i));
			Participant p = participants.get(i);
			participants.remove(i);
			p.getTeam().removeParticipant(p);
			nrOfRemoved++;
		}
		else{
			System.out.println("Error 06: No participant with ID: "+removedID);										
		}
	}
	private void listParticipants(){
		for(Participant p : participants){
			System.out.println(p.toString());
		}
	}
	public Participant getParticipantByID(int id){
		for(Participant p : participants){
			if(p.getID() == id){
				return p;
			}
		}
		return null;
	}
		//result functions
	private void addResult(){
		int pID = inputNumber("Participants ID:").intValue();
		String eventName = normalize(inputString("Event name:"),1);
		boolean incorrectP = true;
		boolean incorrectE = true;
		
		for(Participant p : participants){
			if(p.getID() == pID){
				incorrectP = false;
			}
		}
		Event thisEvent = null;
		int i = 0;
		for(Event e : eventHandler.getAllEvents()){
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
		
		
		if(incorrectP){
			System.out.println("Error 07: Incorrect participant ID given: "+pID);
			if(incorrectE){
				System.out.println("Error 08: Incorrect event name given: "+eventName);
			}
		}
		else if(incorrectE){
			System.out.println("Error 08: Incorrect event name given");
		}
		else{
			double thisResult;
			int attempts = 0;
			do{
				if(attempts>0){
					System.out.println("Error 09: Incorrect input, only results >0 accepted");						
				}					
				thisResult = inputNumber("Result as decimal number:");
			}while(thisResult<0);
			
			if(thisEvent!=null){
				if(!incorrectP && !incorrectE && thisEvent.getTries()>=i){
					Result newResult = new Result(getParticipantByID(pID),eventName,thisResult);
					eventHandler.getEventByName(eventName).addResult(newResult);
					System.out.println("Result: "+ newResult+ " has been added");
				}
			}
		}
	}
		//team functions
	private Team makeTeam(String name){//creates a new team if one with that name does not exist, returns true if makes a team
		if(doesTeamExist(name)){
			return null;
		}
		Team t = new Team(name);
		teams.add(t);
		return t;
	}
	private boolean doesTeamExist(String name){
		for(Team team : teams){
			if(team.getTeamName().equals(name)){
				return true;
			}
		}
		return false;
	}
}
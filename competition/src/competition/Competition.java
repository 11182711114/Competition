package competition;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Scanner;
/*
 * TODO;
 * 	high priority
 * 	med priority
 * 		clean code - somewhat done
 * 	low priority
 */
public class Competition {
	private static final int MESSAGE_NUMBER_CHARS_PER_LINE = 56; //relative number, absolute is +4 , done like this to absolutely avoid going into an infinite loop if it's set to under 4
	private static final char MESSAGE_BOX_CHAR = '*';
	private static final char[] NORMALIZE_FORBIDDEN_CHARACTERS = {};
	
	private File file;
	private File folder = new File("./commands/");
	private Scanner sc;
	
	private Database db;
	private EventHandler eventHandler;
	
	private ArrayList<Participant> participants = new ArrayList<>();
	private int participantID = 100;
	
	
	public static void main(String[] args){
		Competition thisCompetition = new Competition();
		thisCompetition.initialize();
		thisCompetition.run();	
		thisCompetition.exit();
	}
	//main functions
	private void initialize(){
		db = new Database(this);
		eventHandler = new EventHandler(this);
	}
	private void run(){
		menu();
		while(handleCommands(readCommand())){
		}
						
	}
	private void exit(){
		saveDb();
	}
	//database & file functions
	private void saveDb(){
		if(db.databaseSelected()){
			db.writeToFile(eventHandler.getAllEvents(), participants);
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
					Participant part = new Participant(p.getName(),p.getFamilyName(),p.getTeamName(),p.getID(),this);
					participants.add(part);
					part.addParticipantToTeam();
				}
			}
			ArrayList<Event> events = db.getEventsFromDb();
			if(!events.isEmpty()){
				for(Event e : events){
					eventHandler.addEvent(e.getName(),e.getAttempts(),e.getBiggerBetter());
					for(Result r : e.getResults()){
						eventHandler.getEventByName(e.getName()).addResult(r);
						r.getParticipant().addResult(r);
					}
					e.updatePlacement();
				}
			}
		}
		else{
			System.out.println("New database, nothing to load");
		}
	}
	private void setFile(){
		checkCommandFolder(); //make sure the folder exists
		listCommandFiles();
		//get the path to our folder, ask for file name
		String commandFileName;
		do{
			commandFileName = inputString("Choose which database to use or make a new one:");
			if(commandFileName != null && doesFileExist(commandFileName)){ //lets not allow "" as a name
				break;
			}
			else{
				System.out.println("No such file with name: " + commandFileName);
			}
		}while(true);
		file = new File(folder + "/" + commandFileName);
	}
	private void listCommandFiles(){
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles){
			System.out.println(f.getName());
		}
	}
	private boolean isFileSet(){
		if(file!=null){
			return true;
		}
		return false;
	}
	private boolean doesFileExist(String fileName){
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles){
			if(f.getName().equals(fileName)){
				return true;
			}
		}
		return false;
	}
	private void checkCommandFolder(){
		if(!folder.exists() && !folder.isDirectory()){
			folder.mkdir();
		}
	}
	//run functions
	private String readCommand(){
		return normalize(inputString("Listening:"),2);
	}
	private boolean handleCommands(String userInput){
		if(userInput!=null){
			if(userInput.equals("load")){
				loadDb();
			}
			else if(userInput.equals("menu")){
				menu();
			}
			else if(userInput.equals("file")){
				setFile();
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
				eventHandler.addResult();								
			}
			else if(userInput.equals("participant")){
				eventHandler.printResultByParticipant();
			}			
			else if(userInput.equals("teams")){
				printMedals();
				
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
				if(eventHandler.doesEventExist(userInput)){
					eventHandler.printResultByEvent(userInput);
				}
				else{
					System.out.println("Error 00; wrong input given");
					System.out.println("Use \"menu\" to see available commands");
				}
			}
		}
		return true;
	}	
	private void menu(){
		System.out.println("Availible options, non case-sensitive;");
		System.out.println("\"load\" - selects database to get information from and save to");
		System.out.println("\"file\" - selects file to read commands from");
		System.out.println("\"add event\" - adds an event with given options");
		System.out.println("\"add participant\" - adds a participant and gives them an autogenerated ID");
		System.out.println("\"remove participant\" - removes a participant by ID");
		System.out.println("\"participant\" - lists results for participant with given ID");
		System.out.println("\"list participants\" - lists all participants");
		System.out.println("\"add result\" - adds a result for a participant, by ID, for a specific event, by name");
		System.out.println("\"teams\" - lists medals for all teams");
		System.out.println("$eventName - shows the result for $eventName");
		System.out.println("\"message $output\" - prints $output into console in capital letters surrounded by stars");
		System.out.println("\"reinitialize\" - clears all saved information");
		System.out.println("\"exit\" - saves info to db(if selected) and exists program");
	}
	//program functions
		//basic functions
	private void reinitialize(){
		//Reset nrOfRemoved, go through all of the ArrayLists and erase everything
		participantID=100;
		
		eventHandler = new EventHandler(this);
		participants.clear();
	}
	private void message(String s){
		//make new Message, start after "message "
		Message message = new Message(s.substring(8));
		message.printInBox(MESSAGE_NUMBER_CHARS_PER_LINE,MESSAGE_BOX_CHAR);
	}
			//public functions
	public String inputString(String outputGuideString){
		if(isFileSet()){
			String s = readFromFile();
			System.out.println(("# "+s));
			return s;
		}
		@SuppressWarnings("resource")
		Scanner tangentbord = new Scanner(System.in);
		System.out.print(outputGuideString);
		return tangentbord.nextLine();	
	}
	public Double inputNumber(String outputGuideString){
		String dOutput;
		if(isFileSet()){
			String s = readFromFile();
			System.out.println("# "+s);
			dOutput = s;
		}
		else{
		@SuppressWarnings("resource")
		Scanner tangentbord = new Scanner(System.in);
		System.out.print(outputGuideString);
		dOutput = tangentbord.nextLine();
		}
		if(isStringNumber(dOutput)){
			return Double.parseDouble(dOutput);
		}
		else{
			System.out.println(dOutput +" is not a number!");
			return Double.NaN;
		}
	}
	public String normalize(String s, int j){
		/*
		 *remove forbidden character, it will;
		 *remove leading and trailing whitespaces
		 *remove all forbidden characters
		 *leave whitespace if it has characters next to it, e.g. "Boo FF"
		 *
		 *int c;
		 *0 = do nothing
		 *1 = force capitalize
		 *2 = force all lowercase
		*/
		String str = "";
		if(s!=null){
			str = s.trim();
		}
		//check if we trimmed the entire thing
		if(str.isEmpty()){
			return null;
		}
		else{
			String output = null;
			
			for(int i = 0; i<str.length();i++){
				boolean forbidden = false;
				char ch = str.charAt(i);
				for(char forbiddenChar : NORMALIZE_FORBIDDEN_CHARACTERS){
					if(ch == forbiddenChar){
						forbidden = true;
					}
				}
				if(ch == ' '){
					if(str.charAt(i+1)==' '){
						forbidden = true;
					}
				}
				if(!forbidden){
					if(output==null){
						output=String.valueOf(ch);
					}
					else{
						output+=String.valueOf(ch);
					}
				}
			}
			//first letter to uppercase	
			if(j == 1){
				return output.substring(0, 1).toUpperCase()+output.substring(1).toLowerCase();
			}
			//force lowercase
			else if(j == 2){
				return output.toLowerCase();
			}
			//return as is
			return output;
		}
	}
	public boolean isStringNumber(String s){
		try{
			Double.parseDouble(s);
		}catch(Exception e){
			return false;
		}
		return true;
	}
	public ArrayList<Participant> getParticipants(){
		return participants;
	}
		//file functions
	private void setScanner(){
		try {
			sc = new Scanner(new FileReader(file));
		} 
		catch (FileNotFoundException e) {
			System.out.println("Something went wrong reading from file");
		}
	}
	private String readFromFile(){
		if(sc==null){
			setScanner();
		}
		if(sc.hasNextLine()){
			return sc.nextLine();
		}
		else{
			System.exit(0);
		}
		return null;
	}	
		//participant functions
	private void addParticipant(){
		String gName;
		String fName;
		String tName;
		
		do{
			gName = normalize(inputString("Participants given name:"),1);
			if(gName==null){
				System.out.println("Names cannot be empty");
			}
		}while(gName==null);
		
		do{
			fName = normalize(inputString("Participants family name:"),1);
			if(fName==null){
				System.out.println("Names cannot be empty");
			}
		}while(fName==null);
		
		do{
			tName = normalize(inputString("Participants team name:"),1);
			if(tName==null){
				System.out.println("Names cannot be empty");
			}
		}while(tName==null);
		
		while(getParticipantByID(participantID)!=null){
			participantID++;
		}
		Participant newParticipant = new Participant(gName,fName,tName,participantID,this);
		participants.add(newParticipant);
		newParticipant.addParticipantToTeam();
		System.out.println(newParticipant+" added");
		participantID++;
		
	}
	private void removeParticipant(){
		double tempRemovedID = inputNumber("Participant ID to be removed:");
		if(!Double.isNaN(tempRemovedID)){
			int removedID = (int) tempRemovedID;
			Participant p = getParticipantByID(removedID);
			eventHandler.removeResultsByParticipant(p);
			if(participants.remove(p)){
				System.out.println(p + " had been removed");
			}
			else{
				System.out.println("No participant with ID: " + removedID);
			}
		}
	}
	private void listParticipants(){
		for(Participant p : participants){
			System.out.println(p.toString());
		}
	}
			//public functions
	public Participant getParticipantByID(int id){
		for(Participant p : participants){
			if(p.getID() == id){
				return p;
			}
		}
		return null;
	}
	public boolean doesParticipantExist(int id){
		for(Participant p : participants){
			if(p.getID() == id){
				return true;
			}
		}
		return false;
	}
		//team functions
	private ArrayList<Team> getTeams(){
		ArrayList<Team> teams = new ArrayList<>();
		for(Participant p : participants){
			Team t = p.getTeam();
			if(!teams.contains(t)){
				teams.add(t);
			}
		}
		return teams;
	}
	private void printMedals(){
		ArrayList<Team> teams = getTeams();
		Collections.sort(teams);
		
		for(Team t : teams){
			System.out.println(t.getTeamsPlacementsAsString() + " " + t.getName());
		}
	}
}
package competition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Scanner;

public class Database {
	private File folder = new File(new File(".").getAbsolutePath()+"/db");
	private File file;
	private Competition comp;
	
	public Database(Competition c){
		comp = c;
		checkDbFolder();
	}
	public void writeToFile(ArrayList<Event> events, ArrayList<Participant> part){
		//if the db already exists, remove it so that it reflects changes made
		if(file.exists()){
			file.delete();
		}
		try {
			PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
			for(Participant p : part){
				writer.println(p.toDb());
			}
			for(Event e : events){
				ArrayList<Result> results = e.getResults();
				String[] str = e.resultTags();
				writer.println(e.toDb());
				for(Result r : results){
					writer.println(str[0]+r.getResult()+str[1] + str[2]+r.getParticipant().getID()+str[3]);
				}
			}
			writer.close();
		} catch(Exception E){System.out.println("Database error 01");}
	}
	public void setDatabase(){
		file = selectDatabase();
	}
	public File selectDatabase(){
		listDb();
		//get the path to our folder, ask for file name and append ".db" to it
		String db = folder.getAbsolutePath() + "/" + comp.inputString("Choose which database to use or make a new one:") + ".db";
		return new File(db);
	}
	public void listDb(){
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles){
			System.out.println(f.getName());
		}
	}
	public void checkDbFolder(){
		if(!folder.exists() && !folder.isDirectory()){
			folder.mkdir();
		}
	}
	public ArrayList<Participant> getParticipantsFromDb(){
		ArrayList<Participant> parts = new ArrayList<>();
		String[] tags = {"|p|","|/p|" , "|f|","|/f|" , "|t|","|/t|" , "|i|","|/i|"};
		try {
			Scanner sc = new Scanner(new FileReader(file));
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.contains(tags[0])){
					Participant tempParticipant = new Participant(null,null,"",0,comp);
					//iterate by 2 since tags are in pairs
					for(int i = 0; i<tags.length;i+=2){
						switch(tags[i]){
						case "|p|":
							tempParticipant.setName(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1])));
							break;
						case "|f|":
							tempParticipant.setFamilyName(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1])));
							break;
						case "|t|":
							tempParticipant.setTeam(new Team(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1]))));
							break;
						case "|i|":
							tempParticipant.setID(Integer.parseInt(line.substring(line.indexOf(tags[i])+tags[i].length(),line.indexOf(tags[i+1]))));
							break;
						}
					}
					parts.add(tempParticipant);
				}
			}
			sc.close();
			return parts;
		} catch(Exception E){System.out.println("Database error 02");}
		return null;
	}
	public ArrayList<Event> getEventsFromDb(){
		ArrayList<Event> events = new ArrayList<>();
		String[] tags = {"|e|","|/e|" , "|a|","|/a|" , "|b|","|/b|"};
		String[] resultTags = null;
		try {
			Scanner sc = new Scanner(new FileReader(file));
			while(sc.hasNextLine()){
				String line = sc.nextLine();
				if(line.contains(tags[0])){
					Event tempEvent = new Event(null,0,false);
					resultTags = tempEvent.resultTags();
					//iterate by 2 since tags are in pairs
					for(int i = 0; i<tags.length;i+=2){
						switch(tags[i]){
						case "|e|":
							tempEvent.setName(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1])));
							break;
						case "|a|":
							tempEvent.setTries(Integer.parseInt(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1]))));
							break;
						case "|b|":
							tempEvent.setIsBiggerBetter(Boolean.parseBoolean(line.substring(line.indexOf(tags[i])+tags[i].length(), line.indexOf(tags[i+1]))));
							break;
						}
					}
					events.add(tempEvent);
				}
				else if(resultTags!=null && line.contains(resultTags[0])){
					Result tempResult = new Result(null,null,0);
					for(int i = 0; i<resultTags.length;i+=2){
						switch(resultTags[i]){
						case "|r|":
							tempResult.setResult(Double.parseDouble(line.substring(line.indexOf(resultTags[i])+resultTags[i].length(), line.indexOf(resultTags[i+1]))));
							break;
						case "|pID|":
							tempResult.setParticipant(comp.getParticipantByID(Integer.parseInt(line.substring(line.indexOf(resultTags[i])+resultTags[i].length(), line.indexOf(resultTags[i+1])))));
							break;
						}
						tempResult.setEvent(events.get(events.size()-1));
					}
					events.get(events.size()-1).addResult(tempResult);
				}
			}
			sc.close();
			return events;
		} catch(Exception E){System.out.println("Database error 03");}
		return null;
	}
	public boolean databaseExists(){
		if(file.exists()){
			return true;
		}
		else{
			return false;
		}
	}
	public boolean databaseSelected(){
		if(file!=null){
			return true;
		}
		else{
			return false;
		}
	}
}
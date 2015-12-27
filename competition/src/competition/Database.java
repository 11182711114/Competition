package competition;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;

public class Database {
	private File folder = new File(new File(".").getAbsolutePath()+"/db");
	//private File folder = new File("src/competition/db/");
	private File file;
	private Competition comp;
	
	public Database(Competition c){
		comp = c;
		checkDbFolder();
		file = selectDatabase();
	}
	public void writeToFile(ArrayList<Event> events, ArrayList<Participant> part, ArrayList<Team> teams) throws IOException{
			try {
				PrintWriter writer = new PrintWriter(new BufferedWriter(new FileWriter(file,true)));
				for(Event e : events){
					writer.println(e.toDb());
				}
				for(Participant p : part){
					writer.println(p.toDb());
				}
				for(Team t : teams){
					writer.println(t.toDb());
				}
				writer.close();
			} catch (UnsupportedEncodingException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
	}
	public File selectDatabase(){
		listDB();
		String db = folder.getAbsolutePath() + "/" + comp.inputString("Choose which database to use or make a new one>") + ".db";
		System.out.println(db);
		return new File(db);
	}
	public void listDB(){
		File[] listOfFiles = folder.listFiles();
		for(File f : listOfFiles){
			if(f.isFile()){
				System.out.println(f.getName());
			}
		}
	}
	public void checkDbFolder(){
		if(!folder.exists() && !folder.isDirectory()){
			folder.mkdir();
		}
	}
}

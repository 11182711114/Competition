package competition;

import java.util.ArrayList;

public class Team {
	private ArrayList<Participant> participants = new ArrayList<Participant>();
	String name;
	
	public Team(String teamName){
		name = teamName;				
	}
	
	public String getName(){
		return name;
	}
	//removes given participant from participants and returns true if the array is empty
	public boolean removeParticipant(Participant p){
		participants.remove(p);
		if(participants.isEmpty()){
			return true;
		}
		return false;
	}
	public boolean addParticipant(Participant p){
		if(participants.contains(p)){
			return false;
		}
		else{
			participants.add(p);
			return true;
		}
	}
	public String toDb(){
		return "|tn|"+name+"|/tn|";
	}
}
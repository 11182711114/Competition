package competition;

import java.util.ArrayList;

public class Team implements Comparable<Team>{
	private ArrayList<Participant> participants = new ArrayList<Participant>();
	String name;
	
	public Team(String teamName){
		name = teamName;				
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
	@Override
	public int compareTo(Team otherTeam) {
		int[] thisTeamsMedals = getTeamsPlacements();
		int[] otherTeamsMedals = otherTeam.getTeamsPlacements();
		int[] compare = subtractArray(thisTeamsMedals,otherTeamsMedals);
		int isThisTeamLarger = -1; 
		
		if(compare[0]>0){//compare gold medals first
			isThisTeamLarger = -1;
		}
		else if(compare[0]<0){
			isThisTeamLarger = 1;
		}
		else{//if they are the same
			if(compare[1]>0){//compare silver
				isThisTeamLarger = -1;
			}
			else if(compare[1]<0){
				isThisTeamLarger = 1;
			}
			else{//if they are the same
				if(compare[2]>0){//compare bronze
					isThisTeamLarger = -1;
				}
				else if(compare[2]<0){
					isThisTeamLarger = 1;
				}
				else{//if they are the same compare the names
					isThisTeamLarger=this.getName().compareTo(otherTeam.getName());
				}
			}
		}
		return isThisTeamLarger;
	}
	//get and set
	public String getName(){
		return name;
	}
	public ArrayList<Participant> getParticipants(){
		return participants;
	}
	public int[] getTeamsPlacements(){
		int[] medals = {0,0,0};
		
		for(Participant p : participants){
			for(Result r : p.getResults()){
				int placement = r.getPlacementIndex();
				if(placement>0 && placement<=3){
					medals[placement-1]++;
				}
			}
		}
		return medals;
	}
	public String getTeamsPlacementsAsString(){
		String output = "";
		int[] medals = getTeamsPlacements();
		
		for(int i : medals){
			output+=i+" ";
		}
		return output;
	}
	private int[] subtractArray(int[] a1,int[] a2){
		int[] output = new int[a1.length];
		for(int i = 0;i<a1.length;i++){
			output[i] = a1[i]-a2[i];
		}
		return output;
	}
}
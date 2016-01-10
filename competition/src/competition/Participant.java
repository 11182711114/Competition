package competition;

import java.util.ArrayList;

public class Participant {
	private ArrayList<Result> results = new ArrayList<>();
	private String name;
	private String familyName;
	private Team team;
	private int id;
	private Competition competition;
	
	public Participant(String givenName, String familyName, String team, int id, Competition c){
		this.name = givenName;
		this.familyName = familyName;
		this.team = makeTeam(team,c);
		this.id = id;
		this.competition = c;
	}
	private Team makeTeam(String name,Competition comp){//creates a new team if one with that name does not exist, returns true if makes a team
		Team t = doesTeamExist(name, comp);
		if(t==null){
			return new Team(name);
		}
		else{
			return t;
		}
	}
	private Team doesTeamExist(String name, Competition comp){
		for(Participant p : comp.getParticipants()){
			if(p.getTeamName().equals(name)){
				return p.getTeam();
			}
		}
		return null;
	}
	public void addParticipantToTeam(){
		team.addParticipant(this);
	}
	@Override
	public String toString(){
		return "ID:"+id+" "+name+" "+familyName+" Team: "+ team.getName();
	}
	public String toDb(){
		return "|p|"+name+"|/p|" + "|f|"+familyName+"|/f|" + "|t|"+team.getName()+"|/t|" + "|i|"+id+"|/i|";
	}
	public void addResult(Result r){
		results.add(r);
	}
	//get and set
	public void setName(String s){
		name = s;
	}
	public void setFamilyName(String s){
		familyName = s;
	}
	public void setTeam(Team t){
		team = t;
	}
	public void setID(int i){
		id = i;
	}
	public String getName(){
		return name;
	}
	public String getFamilyName(){
		return familyName;
	}
	public String getFullName(){
		return name + " " + familyName;
	}
	public int getID(){
		return id;		
	}
	public Competition getCompetition() {
		return competition;
	}
	public void setCompetition(Competition c) {
		this.competition = c;
	}
	public String namesToString(){
		return name + " " + familyName + " for " + team.getName();
	}
	public Team getTeam(){
		return team;
	}
	public String getTeamName(){
		return team.getName();
	}
	public ArrayList<Result> getResults(){
		return results;
	}
}
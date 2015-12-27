package competition;

public class Participant {
	private String givenName;
	private String familyName;
	private Team team;
	private int id;
	
	public Participant(String gName, String fName, Team tName, int tempID){
		givenName = gName;
		familyName = fName;
		team = tName;
		id = tempID;
	}
	public void setName(String s){
		givenName = s;
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
		return givenName;
	}
	public String getFamilyName(){
		return familyName;
	}
	public int getID(){
		return id;		
	}
	public String namesToString(){
		return givenName + " " + familyName + " for " + team.getTeamName();
	}
	public Team getTeam(){
		return team;
	}
	
	@Override
	public String toString(){
		return "ID:"+id+" "+givenName+" "+familyName+" Team: "+ team.getTeamName();
	}
	public String toDb(){
		return "|p|"+givenName+"|/p|" + "|f|"+familyName+"|/f|" + "|t|"+team.getTeamName()+"|/t|" + "|i|"+id+"|/i|";
	}

}

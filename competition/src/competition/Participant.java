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
	public int getID(){
		return id;		
	}
	public String getNames(){
		return givenName + " " + familyName + " for " + team.getTeamName();
	}
	public Team getTeam(){
		return team;
	}
	@Override
	public String toString(){
		return "ID:"+id+" "+givenName+" "+familyName+" Team: "+ team.getTeamName();
	}

}

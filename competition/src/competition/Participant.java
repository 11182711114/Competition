package competition;

public class Participant {
	private String givenName;
	private String familyName;
	private String teamName;
	private int id;
	
	public Participant(String gName, String fName, String tName, int tempID){
		givenName = gName;
		familyName = fName;
		teamName = tName;
		id = tempID;
	}
	public int getID(){
		return id;		
	}
	public String getNames(){
		return givenName + " " + familyName + " for " + teamName;
	}
	public String getTeam(){
		return teamName;
	}
	@Override
	public String toString(){
		return "ID:"+id+" "+givenName+" "+familyName+" Team: "+teamName;
	}

}

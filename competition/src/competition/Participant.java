package competition;

public class Participant {
	private String givenName;
	private String familyName;
	private Team team;
	private int id;
	private Competition competition;
	
	public Participant(String givenName, String familyName, String team, int id, Competition c){
		this.givenName = givenName;
		this.familyName = familyName;
		this.team = makeTeam(team,c);
		this.id = id;
		this.competition = c;
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
	public String getFullName(){
		return givenName + " " + familyName;
	}
	public int getID(){
		return id;		
	}
	public Competition getC() {
		return competition;
	}
	public void setC(Competition c) {
		this.competition = c;
	}
	public String namesToString(){
		return givenName + " " + familyName + " for " + team.getName();
	}
	public Team getTeam(){
		return team;
	}
	public String getTeamName(){
		return team.getName();
	}
//	public boolean addResult(Result r){
//		if(results.contains(r)){
//			return false;
//		}
//		else{
//			results.add(r);
//			return true;
//		}
//	}
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
		return "ID:"+id+" "+givenName+" "+familyName+" Team: "+ team.getName();
	}
	public String toDb(){
		return "|p|"+givenName+"|/p|" + "|f|"+familyName+"|/f|" + "|t|"+team.getName()+"|/t|" + "|i|"+id+"|/i|";
	}

}
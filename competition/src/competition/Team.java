package competition;

public class Team {
	String name;
	int[] medals;
	
	public Team(String teamName, int[] teamMedals){
		name = teamName;
		medals = teamMedals;		
	}
	public Team(String teamName){
		name = teamName;				
	}
	
	public String getTeamName(){
		return name;
	}
	//0 = gold | 1 = silver | 2 = bronze
	public void addMedal(int medalType){
		medals[medalType]++;
	}
	public void addMedalByArray(int[] newMedals){
		for(int i = 0; i<newMedals.length;i++){
			medals[i]+=newMedals[i];
		}
	}
	
	
}

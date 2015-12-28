package competition;

public class Result implements Comparable<Result>{
	private double results;
	private Participant participant;
	
	public Result(Participant inputParticipant, String eName, double result){
		results = result;
		participant = inputParticipant;
	}
	
	
	public double getResult(){		
		return results;		
	}
	public Participant getParticipant(){
		return participant;
	}
	public void setResult(double d){
		results = d;
	}
	public void setParticipant(Participant p){
		participant = p;
	}
	@Override
	public String toString(){
		return "Result:"+results+" Event name:"+" participant ID: "+participant.getID();
	}
	public int getParticipantID(){
		return participant.getID();
	}
	public String getParticipantName(){
		return participant.getName()+" "+participant.getFamilyName();
	}
	public String getTeamName(){
		return participant.getTeamName();
	}
	/*public int compare(Result a, Result b){
		double compare = a.getResult()-b.getResult();
		if(compare<0){
			return -1;
		}
		else if(compare==0){
			return 0;
		}
		else{
			return 1;
		}
	}*/


	@Override
	public int compareTo(Result r) {
		double compare = results-r.getResult();
		if(compare<0){
			return -1;
		}
		else if(compare==0){
			return 0;
		}
		else{
			return 1;
		}
	}	
}

package competition;

public class Result implements Comparable<Result>{
	private double results;
	private Participant participant;
	private Event event;
	
	public Result(Participant inputParticipant, Event e, double result){
		results = result;
		participant = inputParticipant;
		event = e;
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
	public void setEvent(Event e){
		event = e;
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
	public Team getTeam(){
		return participant.getTeam();
	}
	public String getEventName(){
		return event.getName();
	}
	@Override
	public int compareTo(Result r) {
		double compare = results-r.getResult();
		if(compare>0){
			if(event.getBiggerBetter()){
				return -1;
			}
			else{
				return 1;
			}
		}
		else if(compare==0){
			return 0;
		}
		else{
			if(event.getBiggerBetter()){
				return 1;
			}
			else{
				return -1;
			}
		}
	}	
}
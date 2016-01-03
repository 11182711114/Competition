package competition;

public class Result implements Comparable<Result>{
	private double results;
	private Participant participant;
	private Event event;
	private int placement;
	
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
	public void setPlacement(int i){
		placement = i;
	}
	@Override
	public String toString(){
		return "Result:"+results+" Event name: "+ event.getName() +" participant ID: "+participant.getID();
	}
	public String printResult(){
		return "Placement: "+placement+" Result:"+results+" Event name: "+ event.getName() +" participant ID: "+participant.getID();
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
	public int getPlacement(){
		return placement;
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
package competition;

public class Result implements Comparable<Result>{
	private double result;
	private Participant participant;
	private Event event;
	private Placement placement;
	
	public Result(Participant inputParticipant, Event e, double result){
		this.result = result;
		participant = inputParticipant;
		event = e;
	}
	@Override
	public String toString(){
		return "Result:"+result+" Event name: "+ event.getName() +" participant ID: "+participant.getID();
	}
	public String printResult(){
		return "Placement: "+placement.getPlacement()+" Result:"+result+" Event name: "+ event.getName() +" participant ID: "+participant.getID();
	}
	@Override
	public int compareTo(Result r) {
		double compare = result-r.getResult();
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
	//get and set
	public Event getEvent(){
		return event;
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
	public int getPlacementIndex(){
		if(placement!=null){
			return this.placement.getPlacement();
		}
		return 0;
	}
	public Placement getPlacement() {
		return this.placement;
	}
	public double getResult(){		
		return this.result;		
	}
	public Participant getParticipant(){
		return this.participant;
	}
	public void setResult(double d){
		this.result = d;
	}
	public void setParticipant(Participant p){
		this.participant = p;
	}
	public void setEvent(Event e){
		this.event = e;
	}
	public void setPlacement(Placement place){
		this.placement = place;
	}
}
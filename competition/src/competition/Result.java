package competition;

public class Result {
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
	@Override
	public String toString(){
		return "Result:"+results+" Event name:"+" participant ID: "+participant.getID();
	}
}

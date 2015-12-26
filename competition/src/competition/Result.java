package competition;

public class Result {
	private String name;
	private double results;
	private Participant participant;
	
	public Result(Participant inputParticipant, String eName, double result){
		name = eName;
		results = result;
		participant = inputParticipant;
	}
	
	
	public double getResult(){		
		return results;		
	}
	public Participant getParticipant(){
		return participant;
	}
	public String getNameOfEvent(){
		return name;
	}
	@Override
	public String toString(){
		return "Result:"+results+" Event name:"+name+" participant ID: "+participant.getID();
	}
}

package competition;

public class Result {
	private String name;
	private double results;
	private int participantID;
	
	public Result(int pNumber, String eName, double result){
		name = eName;
		results = result;
		participantID = pNumber;
	}
	
	
	public double getResult(){		
		return results;		
	}
	public int getID(){
		return participantID;
	}
	public String getNameOfEvent(){
		return name;
	}
	@Override
	public String toString(){
		return "Result:"+results+" Event name:"+name+" participant ID: "+participantID;
	}
}

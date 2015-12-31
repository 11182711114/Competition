package competition;

import java.util.ArrayList;
import java.util.Collections;

public class Event {
	private ArrayList<Result> results = new ArrayList<Result>();
	
	private String eventName;
	private int attempts;
	private boolean biggerBetter;
	
	public Event(String eName, int tries, boolean largerBetter){
		 eventName = eName;
		 attempts = tries;
		 biggerBetter = largerBetter;
	}
	public String getName(){
		return eventName;		
	}
	public int getTries(){
		return attempts;		
	}
	public boolean getBiggerBetter(){
		return biggerBetter;
	}
	public void setName(String s){
		eventName = s;
	}
	public void setTries(int i){
		attempts = i;		
	}
	public void setIsBiggerBetter(boolean iBB){
		biggerBetter = iBB;
	}
	public void addResult(Result r){
		results.add(r);
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public boolean checkAllowedMoreAttempts(Participant p){
		if(checkNumberAttempts(p)<attempts){
			return true;
		}
		return false;		
	}
	public int checkNumberAttempts(Participant p){
		int numberAttempts = 0;
		for(Result r : results){
			if(r.getParticipant()==p){
				numberAttempts++;
			}
		}
		return numberAttempts;
	}
	public int[] getMedalsByTeam(Team t){
		/*
		 * iterate through unique results
		 * if the result is equal to the one after it do not increase placement
		 * if placement >3 break;
		 * else check if result.getTeam() = t if so medals[placement-1]+=1
		 */
		int[] medals = {0,0,0};
		ArrayList<Result> results = uniqueResults();
		Collections.sort(results);
		
		int placementIndex = 1;
		int skipNextNumbers = 0;
		boolean printNext = false;
		if(getBiggerBetter()){
			for(int i = 0; i<results.size();i++){
				if(placementIndex>3){
					break;
				}
				//if this result is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
				if(1+i<results.size() && skipNextNumbers==0 && results.get(i).getResult()==results.get(i+1).getResult()){
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					skipNextNumbers++;
					printNext = true;
				}
				//if this result and the one before was equal print this with the same index and increase placementIndex
				else if(printNext){
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					if(1+i<results.size()&& results.get(i).getResult()==results.get(i+1).getResult()){
						printNext = true;
						skipNextNumbers++;
					}
					else{
						printNext = false;
						placementIndex++;
					}
				}
				//if skipNextNumber is >0 we increase placementIndex by 1 and decrease skipNextNumbers by 1
				else if(skipNextNumbers>0){				
					placementIndex++;
					skipNextNumbers--;
					i--;
				}			
				//if the result is not equal to the next we print it out at placementIndex
				else{
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					placementIndex++;
				}		
			}
		}
		else{
			for(int i = 0; i<results.size();i++){
				if(placementIndex>3){
					break;
				}
				//if this result is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
				if(1+i<results.size() && skipNextNumbers==0 && results.get(i).getResult()==results.get(i+1).getResult()){
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					skipNextNumbers++;
					printNext = true;
				}
				//if this result and the one before was equal print this with the same index and increase placementIndex
				else if(printNext){
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					if(1+i<results.size() && results.get(i).getResult()==results.get(i+1).getResult()){
						printNext = true;
						skipNextNumbers++;
					}
					else{
						printNext = false;
						placementIndex++;
					}
				}
				//if skipNextNumber is >0 we increase placementIndex by 1 and decrease skipNextNumbers by 1
				else if(skipNextNumbers>0){
					placementIndex++;
					skipNextNumbers--;
					i--;
				}			
				//if the result is not equal to the next we print it out at placementIndex
				else{
					if(results.get(i).getTeam()==t){
						medals[placementIndex-1]+=1;
					}
					placementIndex++;
				}		
			}
		}
		return medals;
	}
	public ArrayList<Result> uniqueResults(){
		ArrayList<Result> unsortedBestResults = new ArrayList<Result>();
		
		//go through all participants and find their best result and then store it in the output array
		for(Participant thisParticipant : getAllParticipants()){
			Result bestResult = null;
			
			for(Result thisResult : getResults()){
				//check if bigger result is better and if its the right person
				if(getBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
					if(bestResult!=null){
						if(thisResult.getResult()>bestResult.getResult()){
							bestResult = thisResult;
						}
					}
					else{
						bestResult = thisResult;
					}
				}
				//if smaller result is better and if its the right person
				else if(!getBiggerBetter() && (thisResult.getParticipant()==thisParticipant)){
					if(bestResult!=null){
						if(thisResult.getResult()<bestResult.getResult()){
							bestResult = thisResult;
						}
					}
					else{
						bestResult = thisResult;
					}
				}
			}
			if(bestResult!=null){
				unsortedBestResults.add(bestResult);
			}
		}
		return unsortedBestResults;
	}
	private ArrayList<Participant> getAllParticipants(){
		ArrayList<Participant> parts = new ArrayList<>();
		for(Result r : results){
			if(!parts.contains(r.getParticipant())){
				parts.add(r.getParticipant());
			}
		}
		return parts;
	}
	public String toDb(){
		return "|e|"+eventName+"|/e|" + "|a|"+attempts+"|/a|" + "|b|"+biggerBetter+"|/b|";
	}
	public String[] resultTags(){
		String[] str = {"|r|","|/r|" , "|pID|","|/pID|"};
		return str;
	}
}
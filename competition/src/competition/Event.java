package competition;

import java.util.ArrayList;
import java.util.Collections;

public class Event {
	private ArrayList<Result> results = new ArrayList<>();
	private ArrayList<Placement> placements = new ArrayList<>();
	
	private String name;
	private int attempts;
	private boolean biggerBetter;
	
	public Event(String eName, int tries, boolean largerBetter){
		 name = eName;
		 attempts = tries;
		 biggerBetter = largerBetter;
	}
	public void updatePlacement(){
		ArrayList<Result> tempResults = getUniqueResults();
		Collections.sort(tempResults);
		placements.clear();//clear placement to prevent multiple refrences to the same placement
		int placementIndex = 1;
		int skipNextNumbers = 0;
		boolean printNext = false;
		for(int i = 0; i<tempResults.size(); i++){
			Result thisResult = tempResults.get(i);
			Result nextResult = null;
			Placement placement = getPlacementForIndex(placementIndex);
			if(tempResults.size()>i+1){//only set nextResult if we actually have a next result
				nextResult = tempResults.get(i+1);
			}
			//if thisResult is equal to the next result we don't increase placementIndex but we do increase skipNextNumber
			if(nextResult!=null && skipNextNumbers==0 && thisResult.getResult()==nextResult.getResult()){
				thisResult.setPlacement(placement);
				placement.addResult(thisResult);
				addPlacement(placement);
				skipNextNumbers++;
				printNext = true;
			}
			//if thisResult and the one before was equal print this with the same index and increase placementIndex
			else if(printNext){
				thisResult.setPlacement(placement);
				placement.addResult(thisResult);
				addPlacement(placement);
				if(1+i<tempResults.size()&& thisResult.getResult()==nextResult.getResult()){
					printNext = true;
					skipNextNumbers++;
				}
				else{
					printNext = false;
					placementIndex++;
				}
			}
			//if skipNextNumber is >0 we increase placementIndex by 1 and decrease skipNextNumbers by 1 and decrease i since this iteration shouldn't count
			else if(skipNextNumbers>0){				
				placementIndex++;
				skipNextNumbers--;
				i--;
			}			
			//if thisResult is not equal to the next we set it out at placementIndex
			else{
				thisResult.setPlacement(placement);
				placement.addResult(thisResult);
				addPlacement(placement);
				placementIndex++;
			}		
		}
//		for(int i = 0; i<tempResults.size(); i++){
//			Result thisResult = tempResults.get(i);
//			Result nextResult = null;
//			Placement placement = getPlacementForIndex(placementIndex);
//			if(tempResults.size()>i+1){//only set nextResult if we actually have a next result
//				nextResult = tempResults.get(i+1);
//			}
//			if(nextResult!=null && thisResult.getResult() == nextResult.getResult()){
//				thisResult.setPlacement(placement);
//				addPlacement(placement);
//				placementIndex++;
//				while(i<tempResults.size()-1){
//					i++;
//					thisResult = tempResults.get(i);
//					nextResult = tempResults.get(i+1);
//					if(thisResult.getResult() == nextResult.getResult()){
//						thisResult.setPlacement(placement);
//						addPlacement(placement);
//						placementIndex++;
//					}
//					else{
//						placement = getPlacementForIndex(placementIndex);
//						thisResult.setPlacement(placement);
//						addPlacement(placement);
//						placementIndex++;
//						break;
//					}
//				}
//			}
//			else{
//				thisResult.setPlacement(placement);
//				addPlacement(placement);
//				placementIndex++;
//			}
//		}
	}
	private void addPlacement(Placement place){
		if(!placements.contains(place)){
			placements.add(place);
		}
	}
	public Placement getPlacementForIndex(int placement){
		for(Placement place : placements){
			if(place.getPlacement()==placement){
				return place;
			}
		}
		return new Placement(placement);
	}
	public ArrayList<Result> getMedals(){
		ArrayList<Result> medals = new  ArrayList<>();
		for(Result r : results){
			if(r.getPlacementIndex()<4 && r.getPlacementIndex()>0){
				medals.add(r);
			}
		}
		return medals;
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
		int[] medals = {0,0,0};
		for(Result r : results){
			int placement = r.getPlacementIndex();
			if(placement>0 && placement<=3){
				medals[placement]++;
			}
		}
		return medals;
	}
	public ArrayList<Result> removeResultsByParticipant(Participant p){
		ArrayList<Result> outResults = new ArrayList<>();
		for(Result r : results){
			if(r.getParticipant()==p){
				outResults.add(r);
				removeResultFromPlacement(r);
			}
		}
		if(results.removeAll(outResults)){
			return outResults;
		}
		return null;
	}
	public boolean removeResultFromPlacement(Result r){
		for(Placement place : placements){
			boolean isRemoved = place.removeResult(r);
			if(isRemoved){
				return true;
			}
		}
		return false;
	}
	public ArrayList<Result> getUniqueResults(){
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
		return "|e|"+name+"|/e|" + "|a|"+attempts+"|/a|" + "|b|"+biggerBetter+"|/b|";
	}
	public String[] resultTags(){
		String[] str = {"|r|","|/r|" , "|pID|","|/pID|"};
		return str;
	}
	public void addResult(Result r){
		results.add(r);
	}
	//get and set
	public String getName(){
		return name;		
	}
	public int getAttempts(){
		return attempts;		
	}
	public boolean getBiggerBetter(){
		return biggerBetter;
	}
	public void setName(String s){
		name = s;
	}
	public void setAttempts(int i){
		attempts = i;		
	}
	public void setBiggerBetter(boolean iBB){
		biggerBetter = iBB;
	}
	public ArrayList<Result> getResults(){
		return results;
	}
	public ArrayList<Placement> getPlacements(){
		updatePlacement();
		return placements;
	}
}
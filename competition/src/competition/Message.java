package competition;

public class Message {
	private String message;
	
	public Message(String messageIn){
		message = messageIn;
	}
	public void printInBox(int numberOfStars, char boxChar){
		int absoluteNumberOfStars = numberOfStars+4;//add 4 so we dont go into an infinite loop
		int charsWritten = 0;
		printLineOfChar(absoluteNumberOfStars,boxChar);
		printEmptyLineWithStarsOnSides(absoluteNumberOfStars,boxChar);
		
		while(charsWritten<message.length()){
			System.out.print(boxChar+" ");
			for(int i = 0; i<absoluteNumberOfStars-4; i++){
				if(charsWritten<message.length()){
					System.out.print(message.substring(charsWritten, charsWritten+1).toUpperCase());
					charsWritten++;
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println(" "+boxChar);
		}
		
		printEmptyLineWithStarsOnSides(absoluteNumberOfStars,boxChar);
		printLineOfChar(absoluteNumberOfStars,boxChar);
				
	}
	private void printLineOfChar(int numberOfStars,char c){
		for(int i = 0; i<numberOfStars; i++){
			System.out.print(c);
		}
		System.out.println();
	}
	private void printEmptyLineWithStarsOnSides(int numberOfStars, char c){
		for(int i = 0; i<numberOfStars; i++){
			if(i == 0 || i == numberOfStars-1){
				System.out.print(c);
			}
			else{
				System.out.print(" ");
			}
		}
		System.out.println();
	}
}
package competition;

public class Message {
	private String message;
	
	public Message(String messageIn){
		message = messageIn;
	}
	public void printInBoxOfStars(int numberOfStars){
		int absoluteNumberOfStars = numberOfStars+4;//add 4 so we dont go into an infinite loop
		int charsWritten = 0;
		printLineOfStars(absoluteNumberOfStars);
		printEmptyLineWithStarsOnSides(absoluteNumberOfStars);
		
		while(charsWritten<message.length()){
			System.out.print("* ");
			for(int i = 0; i<absoluteNumberOfStars-4; i++){
				if(charsWritten<message.length()){
					System.out.print(message.substring(charsWritten, charsWritten+1).toUpperCase());
					charsWritten++;
				}
				else{
					System.out.print(" ");
				}
			}
			System.out.println(" *");
		}
		
		printEmptyLineWithStarsOnSides(absoluteNumberOfStars);
		printLineOfStars(absoluteNumberOfStars);
				
	}
	private void printLineOfStars(int numberOfStars){
		for(int i = 0; i<numberOfStars; i++){
			System.out.print("*");
		}
		System.out.println();
	}
	private void printEmptyLineWithStarsOnSides(int numberOfStars){
		for(int i = 0; i<numberOfStars; i++){
			if(i == 0 || i == numberOfStars-1){
				System.out.print("*");
			}
			else{
				System.out.print(" ");
			}
		}
		System.out.println();
	}
}
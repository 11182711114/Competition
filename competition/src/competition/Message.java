package competition;

public class Message {
	
	
	private String message;
	
	public Message(String messageIn){
		message = messageIn;
	}
	public void printInBoxOfStars(int numberOfStars){
		int charsWritten = 0;
		printLineOfStars(numberOfStars);
		printEmptyLineWithStarsOnSides(numberOfStars);
		
		while(charsWritten<message.length()){
			System.out.print("* ");
			for(int i = 0; i<numberOfStars-4; i++){
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
		
		printEmptyLineWithStarsOnSides(numberOfStars);
		printLineOfStars(numberOfStars);
				
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
package competition;

public class Message {
	private static final int ABSOLUTE_NUMBER_CHARS_PER_LINE = 60;
	
	private String message;
	
	public Message(String messageIn){
		message = messageIn;
	}
	public void printMessage(){
		int charsWritten = 0;
		printLineOfStars();
		
		while(charsWritten<message.length()){
			System.out.print("* ");
			for(int i = 0; i<ABSOLUTE_NUMBER_CHARS_PER_LINE-4; i++){
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
			
		printLineOfStars();
				
	}
	private void printLineOfStars(){
		for(int i = 0; i<ABSOLUTE_NUMBER_CHARS_PER_LINE; i++){
			System.out.print("*");
		}
		System.out.println();
	}
}

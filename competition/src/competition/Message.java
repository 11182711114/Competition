package competition;

public class Message {
	final private int ABSOLUTE_NUMBER_CHARS_PER_LINE = 60;
	
	private String message;
	
	public Message(String messageIn){
		message = messageIn;
	}
	public void outputString(){
		int charsWritten = 0;
		printLineOfStars();
		
		while(charsWritten<=message.length()){
			System.out.print("* ");
			for(int i = 0; i<ABSOLUTE_NUMBER_CHARS_PER_LINE; i++){
				if(charsWritten>=message.length()){
					System.out.print(" ");
				}
				else{
					System.out.print(message.substring(charsWritten, charsWritten+1));
					charsWritten++;
				}
			}
			System.out.println(" *");
		}
			
		printLineOfStars();
				
	}
	public void printLineOfStars(){
		for(int i = 0; i<ABSOLUTE_NUMBER_CHARS_PER_LINE; i++){
			System.out.print("*");
		}
	}
}

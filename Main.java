

import java.util.Scanner;

public class Main implements ConnectionDelegate{
	
	public static void main(String[] args) {
		UserPrompt prompt = new UserPrompt();
		prompt.setVisible(true);
		
	}

	@Override
	public void handleMessage(String theMessage) {
		System.out.println(theMessage);
	}
	
}

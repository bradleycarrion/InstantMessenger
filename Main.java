

import java.util.Scanner;

public class Main implements ConnectionDelegate{
	
	public static void main(String[] args) {
		
		Scanner scan = new Scanner(System.in);
		System.out.print("Username: ");
		String userName = scan.nextLine();
		System.out.print("Password: ");
		String pw = scan.nextLine();
		System.out.print("Server: ");
		String server = scan.nextLine();
		
		Connection connect = new Connection(userName, pw, server);
		connect.setDelegate(new Main());
		connect.connect();
		System.out.print("Who do you want to chat: ");
		String other = scan.nextLine();
		connect.startChat(other);
		
		while (true) {
			String message = scan.nextLine();
			connect.sendMessage(message);
		}
	}

	@Override
	public void handleMessage(String theMessage) {
		System.out.println(theMessage);
	}
	
}

package utwente.ss.connect.client.view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import utwente.ss.connect.client.controller.ClientController;
import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.Util;

public class TuiView {

	private ClientController client;

	private Scanner in;

	public TuiView(ClientController client) {
		this.client = client;
		in = new Scanner(System.in);
	}

	public InetAddress connectServer() {
		InetAddress address = null;
		do {
			try {
				String host = readString("Enter the hostname.");
				address = InetAddress.getByName(host);
			} catch (UnknownHostException e) {
				client.addMessage("Error reading hostname, please try again.");
				address = null;
			}

		} while (address == null);
		return address;
	}
	
	public int getPort() {
		int port = -1;
		do {
			port = readInteger("Enter the port, enter 0 for default:" 
					+ " [" + Protocol.PORTNUMBER + "]");
		} while (port != -1 && !Util.available(port));
		if (port == 0) {
			return Protocol.PORTNUMBER;
		}
		return port;
	}
	
	public int getPlayerType() {
		int player = -1;
		do {
			player = readInteger("What kind of player are you? enter 0 for human player, 1 for naive computerplayer, 2 for smart computerplayer:") ;
		} while (player == -1);
		return player;
	}
	
	private boolean readBoolean(String prompt, String yes, String no) {
		String answer;
		do {
			client.addMessage(prompt);
			answer = in.nextLine();
		} while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
		return answer.equals(yes);
	}

	private int readInteger(String prompt) {
		Integer answer;
		do {
			client.addMessage(prompt);
			answer = in.nextInt();
			in.nextLine(); //Needed because nextInt doesnt skip to next line
		} while (answer == null);
		return answer;
	}

	private String readString(String prompt) {
		String answer = null;
		do {
			client.addMessage(prompt);
			answer = in.nextLine();
		} while (answer == null || answer.length() < 1);
		return answer;
	}

	public String getPlayername() {
		return readString("Enter your player name.");
	}
	
	public int[] askMove() {
		int[] answer = new int[2];
		answer[0] = readInteger("Enter x coordinate");
		answer[1] = readInteger("Enter z coordinate");
		return answer;
	}
	
	public boolean askStartAgain() {
		return readBoolean("Want to start a new game? (y/n)", "y", "n");
	}

}

package utwente.ss.connect.client.view;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Scanner;

import utwente.ss.connect.client.controller.ClientController;

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

}

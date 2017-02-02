package utwente.ss.connect.server.view;

import java.util.Scanner;

import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.Util;
import utwente.ss.connect.server.controller.ServerController;

public class TuiView {
	private ServerController server;

	private Scanner in;

	public TuiView(ServerController server) {
		this.server = server;
		in = new Scanner(System.in);
	}

	private int readInteger(String prompt) {
		Integer answer;
		do {
			server.addMessage(prompt);
			answer = in.nextInt();
			in.nextLine();
		} while (answer == null);
		return answer;
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
}

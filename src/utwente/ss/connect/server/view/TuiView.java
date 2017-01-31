package utwente.ss.connect.server.view;

import java.io.IOException;
import java.net.DatagramSocket;
import java.net.ServerSocket;
import java.util.Scanner;

import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.server.controller.ServerController;

public class TuiView {
	private ServerController server;

	private Scanner in;

	public TuiView(ServerController server) {
		this.server = server;
		in = new Scanner(System.in);
	}

	private boolean readBoolean(String prompt, String yes, String no) {
		String answer;
		do {
			server.addMessage(prompt);
			answer = in.nextLine();
		} while (answer == null || (!answer.equals(yes) && !answer.equals(no)));
		return answer.equals(yes);
	}

	private int readInteger(String prompt) {
		Integer answer;
		do {
			server.addMessage(prompt);
			answer = in.nextInt();
		} while (answer == null);
		return answer;
	}

	private String readString(String prompt) {
		String answer = null;
		do {
			server.addMessage(prompt);
			answer = in.nextLine();
		} while (answer == null || answer.length() < 1);
		return answer;
	}

	public int getPort() {
		int port = -1;
		do {
			port = readInteger("Enter the port, enter 0 for default:" 
					+ " [" + Protocol.PORTNUMBER + "]");
		} while (port != -1 && !available(port));
		if (port == 0) {
			return Protocol.PORTNUMBER;
		}
		return port;
	}

	/**
	 * Checks to see if a specific port is available.
	 * http://stackoverflow.com/questions/434718/sockets-discover-port-availability-using-java
	 *
	 * @param port
	 *            the port to check for availability
	 */
	public static boolean available(int port) {
		ServerSocket ss = null;
		DatagramSocket ds = null;
		try {
			ss = new ServerSocket(port);
			ss.setReuseAddress(true);
			ds = new DatagramSocket(port);
			ds.setReuseAddress(true);
			return true;
		} catch (IOException e) {
		} finally {
			if (ds != null) {
				ds.close();
			}

			if (ss != null) {
				try {
					ss.close();
				} catch (IOException e) {
					/* should not be thrown */
				}
			}
		}

		return false;
	}
}

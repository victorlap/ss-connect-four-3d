package utwente.ss.connect.client.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.InetAddress;
import java.net.Socket;

import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.exception.BadMoveException;

public class NetworkController extends Thread implements Protocol {

	private ClientController controller;
	private InetAddress server;
	private int port;

	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;

	private boolean isRunning = false;

	public NetworkController(InetAddress server, int port, ClientController controller) {
		super();

		this.server = server;
		this.controller = controller;
		this.port = port;
	}

	/**
	 * Reads the messages in the socket connection. Each message will be
	 * forwarded to the ClientController
	 */
	public void run() {
		try {

			controller.addMessage(
					"Connecting to server on " + server.getHostName() + ":" + port + ".");

			sock = new Socket(server, port);
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));

			isRunning = true;

			// Start all communications
			sendMessage(Protocol.CLIENT_JOINREQUEST + DELIM + controller.getMe().getName() + DELIM
					+ "0 0 0 0");

			while (isRunning) {
				if (in.ready()) {
					String command = in.readLine();
					if (command != null && !command.isEmpty()) {
						execute(command);
					} else {
						shutdown();
					}
				}
			}
		} catch (IOException e) {
			shutdown();
		} catch (BadMoveException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		shutdown();
	}

	/**
	 * Sends a message to the server.
	 * 
	 * @param msg
	 */
	public void sendMessage(String msg) {
		try {
			// controller.addMessage(msg);
			out.write(msg + "\n");
			out.flush();
		} catch (IOException e) {
			controller.addMessage("Sending message: " + msg + " failed!");
		}
	}

	/**
	 * Shuts the server down and closes all open connections.
	 */
	public void shutdown() {
		controller.addMessage("Shutting down.");

		isRunning = false;

		try {
			if (out != null) {
				out.close();
			}
			if (in != null) {
				in.close();
			}
			if (sock != null) {
				sock.close();
			}
		} catch (IOException e) {
			controller.addMessage("IO Exception occured!");
		}
		controller.deadConnection();
	}

	/**
	 * Execute the given command from the server.
	 * 
	 * @param command
	 * @throws BadMoveException
	 */
	public void execute(String commandline) throws BadMoveException {
		String[] commandlineSplit = commandline.split(" ");

		String command = commandlineSplit[0];
		String[] args = new String[commandlineSplit.length - 1];
		System.arraycopy(commandlineSplit, 1, args, 0, commandlineSplit.length - 1);

		switch (command) {
			case SERVER_ACCEPTREQUEST:
				sendMessage(CLIENT_GAMEREQUEST);
				break;
			case SERVER_DENYREQUEST:
				String name = args[0];
				controller.addMessage("Username " + name + " is already in use!");
				controller.usernameInUse();
				break;
			case SERVER_WAITFORCLIENT:
				controller.addMessage("Waiting for other player...");
				break;
			case SERVER_STARTGAME:
				String opponent = otherUser(args[0], args[1]);
				controller.addMessage("Starting game against " + opponent + ".");
				controller.startGame(opponent);
				break;
			case SERVER_MOVEREQUEST:
				if (args[0].equals(controller.getMe().getName())) {
					controller.addMessage("It's your turn to do a move!");
					controller.askMove();
				} else {
					controller.addMessage("It's " + args[0] + "s turn to do a move!");
				}
				break;
			case SERVER_DENYMOVE:
				controller.addMessage("Whoops! This move is not valid! Try again.");
				controller.askMove();
				break;
			case SERVER_NOTIFYMOVE:
				controller.addMessage(args[0] + " placed a move on x = " + args[1] + " y = "
						+ args[2] + " z = " + args[3]);
				controller.notifyMove(args[1], args[3], args[0]);
				break;
			case SERVER_GAMEOVER:
				controller.addMessage(args[0] + " wins the game!");
				controller.askStartAgain();
				break;
			case SERVER_CONNECTIONLOST:
				controller.addMessage(args[0] + " has disconnected from the server.");
				controller.askStartAgain();
				break;
			case SERVER_INVALIDCOMMAND:
				controller.addMessage("Whoops! Something went wrong!");
				break;
			case SERVER_BROADCASTMESSAGE:
			case SERVER_CHALLENGELIST:
			case SERVER_RESULTCHALLENGE:
			case SERVER_BROADCASTLEADERBOARD:
			default:
				break;
		}
	}

	/**
	 * Gives back the opponents username.
	 * 
	 * @param one
	 *            Username one
	 * @param two
	 *            Username two
	 * @return the username of the opponent.
	 */
	public String otherUser(String one, String two) {
		if (controller.getMe().getName().equals(one)) {
			return two;
		}
		return one;
	}

}

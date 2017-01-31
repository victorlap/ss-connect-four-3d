package utwente.ss.connect.server.controller;

import java.io.IOException;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.model.Game;
import utwente.ss.connect.common.model.Player;

public class NetworkController extends Thread implements Protocol {

	private ServerController controller;
	private int port;

	private ServerSocket sock;
	private boolean isRunning = false;

	private Collection<ClientHandlerController> clients;
	private Collection<Game> games;

	public NetworkController(ServerController controller, int port) {
		super();

		this.controller = controller;
		this.port = port;

		this.clients = new ArrayList<>();
		this.games = new ArrayList<>();
	}

	/**
	 * Accepts new connections and sets up a new clienthandler for each
	 * incoming. connection
	 */
	public void run() {
		try {
			sock = new ServerSocket(port);
			isRunning = true;

			dispalyIpAddresses();

			while (isRunning) {

				Socket newSocket = sock.accept();
				ClientHandlerController newHandler = new ClientHandlerController(this, newSocket, controller);
				controller.addMessage("New Connection from: " + newSocket.getInetAddress().getHostName());
				addHandler(newHandler);
				newHandler.start();
			}
		} catch (IOException e) {
			controller.addMessage("Server couldn't start because the port is not available");
		}
	}

	public void dispalyIpAddresses() {
		try {
			InetAddress localhost = InetAddress.getLocalHost();
			controller.addMessage(" IP Addr: " + localhost.getHostAddress() + ":" + port);

			InetAddress[] allMyIps = InetAddress.getAllByName(localhost.getCanonicalHostName());
			if (allMyIps != null && allMyIps.length > 1) {
				controller.addMessage(" Full list of IP addresses:");
				for (int i = 0; i < allMyIps.length; i++) {
					controller.addMessage("    " + allMyIps[i]);
				}
			}
		} catch (UnknownHostException e) {
			controller.addMessage(" (error retrieving server host name)");
		}
	}

	/**
	 * Add new ClientHandler.
	 */
	public void addHandler(ClientHandlerController handler) throws IOException {
		clients.add(handler);
	}

	/**
	 * Remove a client handler and notify (possible) opponents.
	 * 
	 * @param handler
	 */
	public void removeHandler(ClientHandlerController handler) {
		Game g = getGame(handler.getPlayer());
		broadcast(SERVER_CONNECTIONLOST + " " + handler.getPlayer().getName(), getHandlers(g.getPlayers()));

		g.removePlayer(handler.getPlayer());
		clients.remove(handler);
	}

	/**
	 * Get all ClientHandlers by the player instance.
	 * 
	 * @param players
	 * @return
	 */
	private Collection<ClientHandlerController> getHandlers(List<Player> players) {
		return clients.stream().filter(c -> players.contains(c.getPlayer())).collect(Collectors.toList());
	}

	private Collection<ClientHandlerController> getHandler(Player player) {
		return getHandlers(Arrays.asList(player));
	}

	/**
	 * Get game by player.
	 */
	public Game getGame(Player byPlayer) {
		for (Game game : games) {
			for (Player player : game.getPlayers()) {
				if (player.equals(byPlayer)) {
					return game;
				}
			}
		}
		return null;
	}

	/**
	 * Checks if the username is in use.
	 */
	public boolean isUsernameInUse(String username) {
		for (ClientHandlerController handler : clients) {
			if (handler.getPlayer().getName().equals(username)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the player is currently in a game.
	 */
	public boolean isInGame(Player player) {
		return getGame(player) != null;
	}

	/**
	 * Executes the commands coming from the clients.
	 * 
	 * @param command
	 * @param sender
	 */
	public void execute(String commandline, ClientHandlerController sender) {
		controller.addMessage(commandline);
		String[] commandlineSplit = commandline.split(" ");

		String command = commandlineSplit[0];
		String[] args = new String[commandlineSplit.length - 1];
		System.arraycopy(commandlineSplit, 1, args, 0, commandlineSplit.length - 1);

		switch (command) {
			case CLIENT_JOINREQUEST:
				if (!isUsernameInUse(args[0])) {
					sender.setName(args[0]);
					broadcast(SERVER_ACCEPTREQUEST + DELIM + args[0] + DELIM + "0 0 0 0", sender);
				} else {
					broadcast(SERVER_DENYREQUEST + DELIM + args[0], sender);
				}
				break;
			case CLIENT_GAMEREQUEST:
				addUserToGame(sender.getName());
				break;
			case CLIENT_SETMOVE:
				// TODO
				break;
			case CLIENT_SENDMESSAGE:
			case CLIENT_REQUESTCHALLENGELIST:
			case CLIENT_REQUESTCHALLENGE:
			case CLIENT_ANSWERCHALLENGE:
			case CLIENT_REQUESTLEADERBOARD:
			case CLIENT_SETLEADERBOARD:
			default:
				broadcast(SERVER_INVALIDCOMMAND, sender);
				break;
		}
	}

	/**
	 * Broadast a message to all clients.
	 * 
	 * @param msg
	 */
	public void broadcast(String msg) {
		broadcast(msg, this.clients);
	}

	/**
	 * Broadcast a message to specific ClientHandlers.
	 * 
	 * @param msg
	 * @param clients
	 */
	public void broadcast(String msg, Collection<ClientHandlerController> handlers) {
		if (msg != null) {
			for (ClientHandlerController handler : handlers) {
				handler.sendMessage(msg);
			}
			controller.addMessage("[BROADCAST] " + msg);
		}
	}

	/**
	 * Broadcast a message to a single client.
	 * 
	 * @param msg
	 * @param client
	 */
	public void broadcast(String msg, ClientHandlerController client) {
		if (msg != null && client != null) {
			client.sendMessage(msg);
			controller.addMessage("[BROADCAST to " + client.getPlayer().getName() + "] " + msg);
		}
	}

	public void addUserToGame(String name) {
		Player player = new Player(name);
		Game game = findFreeGame();
		game.addPlayer(player);

		if (game.getPlayers().size() == 2) {
			game.start();
			broadcast(SERVER_STARTGAME + DELIM + game.getPlayerString(), getHandlers(game.getPlayers()));
		} else {
			broadcast(SERVER_WAITFORCLIENT, getHandler(player));
		}
	}

	private Game findFreeGame() {
		for (Game game : games) {
			if (!game.hasStarted) {
				return game;
			}
		}
		Game game = new Game();
		games.add(game);
		return game;
	}

}

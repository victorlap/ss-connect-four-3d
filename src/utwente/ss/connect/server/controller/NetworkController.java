package utwente.ss.connect.server.controller;

import java.io.IOException;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.ArrayList;
import java.util.Collection;
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
	
	public NetworkController(ServerController controller) {
		super();
		
		this.controller = controller;
		this.port = PORTNUMBER;
		
		this.clients = new ArrayList<>();
		this.games = new ArrayList<>();
	}
	

	/**
	 * Accepts new connections and sets up a new clienthandler for each incoming connection
	 */
	public void run() {
		try {
			sock = new ServerSocket(port);
			isRunning = true;

			while(isRunning) {

				Socket newSocket = sock.accept();
				ClientHandlerController newHandler = new ClientHandlerController(this, newSocket, controller);
				controller.addMessage("New Connection from: "+ newSocket.getInetAddress().getHostName());
				addHandler(newHandler);
				newHandler.start();
			}	
		} catch (IOException e) {
			controller.addMessage("Server couldn't start because the port is not available");
		}
	}
	
	/**
	 * Add new ClientHandler
	 */
	public void addHandler(ClientHandlerController handler) throws IOException {
		clients.add(handler);
	}
	
	/**
	 * Remove a client handler and notify (possible) opponents
	 * @param handler
	 */
	public void removeHandler(ClientHandlerController handler) {
		Game g = getGame(handler.getPlayer());
		broadcast(SERVER_CONNECTIONLOST + " "+ handler.getPlayer().getName(), getHandlers(g.getPlayers()));
		
		g.removePlayer(handler.getPlayer());
		clients.remove(handler);
	}
	
	/**
	 * Get all ClientHandlers by the player instance
	 * @param players
	 * @return
	 */
	private Collection<ClientHandlerController> getHandlers(ArrayList<Player> players) {
		return clients.stream()
			.filter(c -> players.contains(c.getPlayer()))
			.collect(Collectors.toList());
	}

	/**
	 * Get game by player
	 */
	public Game getGame(Player byPlayer) {
		for(Game game: games) {
			for(Player player : game.getPlayers()) {
				if(player.equals(byPlayer)) {
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
		for(ClientHandlerController handler : clients) {
			if(handler.getPlayer().getName().equals(username)) {
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
	 * Executes the commands coming from the clients
	 * @param command
	 * @param sender
	 */
	public void execute(String command, ClientHandlerController sender) {
	}
	
	/**
	 * Broadast a message to all clientse	
	 * @param msg
	 */
	public void broadcast(String msg) {
		broadcast(msg, this.clients);
	}
	
	/**
	 * Broadcast a message to specific ClientHandlers
	 * @param msg
	 * @param clients
	 */
	public void broadcast(String msg, Collection<ClientHandlerController> clients) {
		if(msg != null) {
			for(ClientHandlerController handler : clients) {
				handler.sendMessage(msg);
			}
			controller.addMessage("[BROADCAST] "+ msg);
		}
	}
	
	/**
	 * Broadcast a message to a single client
	 * @param msg
	 * @param client
	 */
	public void broadcast(String msg, ClientHandlerController client) {
		if(msg != null && client != null) {
			client.sendMessage(msg);
			controller.addMessage("[BROADCAST to "+ client.getPlayer().getName() +"] "+ msg);
		}
	}
	
	/**
	 * Starts a new game
	 * @param game
	 */
	private void startGame(Game game) {
		System.out.println("In startgame()");
		broadcast(SERVER_STARTGAME + " " + game.getPlayerString(), getHandlers(game.getPlayers()));
		game.start();
	}

}

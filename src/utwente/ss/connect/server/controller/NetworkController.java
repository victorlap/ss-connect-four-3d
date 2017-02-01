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
import java.util.Observable;
import java.util.Observer;
import java.util.stream.Collectors;

import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.exception.BadMoveException;
import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Colour;
import utwente.ss.connect.common.model.Game;
import utwente.ss.connect.common.model.players.Player;

public class NetworkController extends Thread implements Protocol, Observer {

	private ServerController controller;
	private int port;

	private ServerSocket sock;
	private boolean isRunning = false;

	private Collection<ClientHandlerController> clients;
	private Collection<Game> games;

	/**
	 * Start a new NetworkController. This controller is responsible for all
	 * incoming and outgoing messages to the clients.
	 * 
	 * @param controller
	 * @param port
	 */
	public NetworkController(ServerController controller, int port) {
		super();

		this.controller = controller;
		this.port = port;

		this.clients = new ArrayList<ClientHandlerController>();
		this.games = new ArrayList<Game>();
	}

	/**
	 * Accepts new connections and sets up a new clienthandler for each incoming
	 * connection.
	 */
	public void run() {
		try {
			sock = new ServerSocket(port);
			isRunning = true;

			displayIpAddresses();

			while (isRunning) {

				Socket newSocket = sock.accept();
				ClientHandlerController newHandler = new ClientHandlerController(this, newSocket,
						controller);
				controller.addMessage(
						"New Connection from: " + newSocket.getInetAddress().getHostName());
				addHandler(newHandler);
				newHandler.start();
			}
		} catch (IOException e) {
			controller.addMessage("Server couldn't start because the port is not available");
		}
	}

	/**
	 * Print all the known ipaddresses to the controller.
	 */
	//@pure
	private void displayIpAddresses() {
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
		broadcast(SERVER_CONNECTIONLOST + " " + handler.getPlayer().getName(),
				getHandlers(g.getPlayers()));

		g.removePlayer(handler.getPlayer());
		clients.remove(handler);
	}

	/**
	 * Get all ClientHandlers by the player instances.
	 * 
	 * @param players
	 * @return
	 */
	//@pure
	private Collection<ClientHandlerController> getHandlers(List<Player> players) {
		return clients.stream().filter(c -> players.contains(c.getPlayer()))
				.collect(Collectors.toList());
	}

	/**
	 * Get all ClientHandlers by the player instance.
	 * 
	 * @param player
	 * @return
	 */
	//@pure
	private Collection<ClientHandlerController> getHandler(Player player) {
		return getHandlers(Arrays.asList(player));
	}

	/**
	 * Get game by Player.
	 * 
	 * @param byPlayer
	 * @return
	 */
	//@pure
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
	//@pure
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
	//@pure
	public boolean isInGame(Player player) {
		return getGame(player) != null;
	}

	/**
	 * Executes the commands coming from the clients.
	 * 
	 * @param command
	 * @param sender
	 * @throws BadMoveException
	 */
	public void execute(String commandline, ClientHandlerController sender)
			throws BadMoveException {
		String[] commandlineSplit = commandline.split(" ");

		String command = commandlineSplit[0];
		String[] args = new String[commandlineSplit.length - 1];
		System.arraycopy(commandlineSplit, 1, args, 0, commandlineSplit.length - 1);

		switch (command) {
			case CLIENT_JOINREQUEST:
				if (args.length < 1) {
					broadcast(SERVER_INVALIDCOMMAND, sender);
					break;
				}
				if (!isUsernameInUse(args[0])) {
					sender.getPlayer().setName(args[0]);
					broadcast(SERVER_ACCEPTREQUEST + DELIM + args[0] + DELIM + "0 0 0 0", sender);
				} else {
					broadcast(SERVER_DENYREQUEST + DELIM + args[0], sender);
				}
				break;
			case CLIENT_GAMEREQUEST:
				addUserToGame(sender.getPlayer());
				break;
			case CLIENT_SETMOVE:
				if (args.length < 2) {
					broadcast(SERVER_INVALIDCOMMAND, sender);
					break;
				}
				if (isTurn(sender.getPlayer())) {
					try {
						controller.addMessage(command);
						int x = Integer.parseInt(args[0]);
						int y = Integer.parseInt(args[1]);
						getGame(sender.getPlayer()).doMove(x, y, sender.getPlayer().getBead());
					} catch (NumberFormatException e) {
						broadcast(SERVER_INVALIDCOMMAND, sender);
					} catch (BadMoveException e) {
						broadcast(SERVER_DENYMOVE, sender);
					}
				} else {
					broadcast(SERVER_DENYMOVE, sender);
				}
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
	 * Is it the turn of the player?
	 * 
	 * @param player
	 * @return
	 */
	//@pure
	public boolean isTurn(Player player) {
		Game g = getGame(player);
		return g.isTurn(player);
	}

	/**
	 * Broadast a message to all clients.
	 * 
	 * @param msg
	 */
	//@pure
	public void broadcast(String msg) {
		broadcast(msg, this.clients);
	}

	/**
	 * Broadcast a message to specific ClientHandlers.
	 * 
	 * @param msg
	 * @param clients
	 */
	//@pure
	public void broadcast(String msg, Collection<ClientHandlerController> handlers) {
		if (msg != null) {
			for (ClientHandlerController handler : handlers) {
				handler.sendMessage(msg);
			}
		}
	}

	/**
	 * Broadcast a message to a single client.
	 * 
	 * @param msg
	 * @param client
	 */
	//@pure
	public void broadcast(String msg, ClientHandlerController client) {
		if (msg != null && client != null) {
			client.sendMessage(msg);
		}
	}

	/**
	 * Add player to a (new) game.
	 * 
	 * @param player
	 */
	/*@ ensures player.getBead().getColour() == Colour.YELLOW 
	 		|| player.getBead().getColour() == Colour.RED;
	 */
	public void addUserToGame(Player player) {
		Game game = findFreeGame();
		game.addPlayer(player);

		if (game.getPlayers().size() == 2) {
			player.setBead(new Bead(Colour.RED));
			game.start();
			broadcast(SERVER_STARTGAME + DELIM + game.getPlayerString(),
					getHandlers(game.getPlayers()));

			broadcast(SERVER_MOVEREQUEST + DELIM + game.getCurrent().getName(),
					getHandler(game.getCurrent()));
		} else {
			player.setBead(new Bead(Colour.YELLOW));
			broadcast(SERVER_WAITFORCLIENT, getHandler(player));
		}
	}

	/**
	 * Find an open game for a player, if there is no open game, create a new
	 * one and prepare it for playing
	 * 
	 * @return
	 */
	/*@ ensures \result.hasStarted == false; */
	private Game findFreeGame() {
		for (Game game : games) {
			if (!game.hasStarted) {
				return game;
			}
		}
		Game game = new Game();
		games.add(game);
		game.addObserver(this);
		return game;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public void update(Observable obs, Object obj) {
		if (obs instanceof Game) {
			Game game = (Game) obs;
			broadcast(SERVER_NOTIFYMOVE + DELIM + game.getLastMoveString(),
					getHandlers(game.getPlayers()));

			if (game.hasEnded()) {
				games.remove(game);
				if (game.hasWinner()) {
					broadcast(SERVER_GAMEOVER + DELIM + game.getWinner().getName(),
							getHandlers(game.getPlayers()));
				} else {
					broadcast(SERVER_GAMEOVER, getHandlers(game.getPlayers()));
				}
			} else {
				broadcast(SERVER_MOVEREQUEST + DELIM + game.getCurrent().getName(),
						getHandler(game.getCurrent()));
			}
		}
	}

}
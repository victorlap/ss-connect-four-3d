package utwente.ss.connect.client.controller;

import java.net.InetAddress;

import utwente.ss.connect.client.view.TuiView;
import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.exception.BadMoveException;
import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Colour;
import utwente.ss.connect.common.model.Game;
import utwente.ss.connect.common.model.players.ComputerPlayer;
import utwente.ss.connect.common.model.players.HumanPlayer;
import utwente.ss.connect.common.model.players.Player;
import utwente.ss.connect.common.model.strategies.NaiveStrategy;
import utwente.ss.connect.common.model.strategies.SmartStrategy;

public class ClientController {

	private Game game;

	private Player me;

	private TuiView view;

	private NetworkController network;

	public ClientController() {
		view = new TuiView(this);
		game = new Game();
	}

	/**
	 * Add a message to the UI.
	 * 
	 * @param msg
	 */
	public void addMessage(String msg) {
		System.out.println(msg);
	}

	/**
	 * Handle dead connections.
	 */
	public void deadConnection() {
		addMessage("Connection to the server is lost.");
		System.exit(0);
	}

	public void start() {
		InetAddress address = view.connectServer();
		int port = view.getPort();

		int player = view.getPlayerType();

		switch (player) {
			case 0:
				me = new HumanPlayer();
				break;
			case 1:
				me = new ComputerPlayer(new NaiveStrategy());
				me.setThinkingTime(view.getThinkingTime());
				break;
			case 2:
				me = new ComputerPlayer(new SmartStrategy());
				me.setThinkingTime(view.getThinkingTime());
				break;
		}

		me.setName(view.getPlayername());

		game.addPlayer(me);

		network = new NetworkController(address, port, this);
		network.start();
	}

	public void usernameInUse() {
		game.removePlayer(me);

		me.setName(view.getPlayername());

		game.addPlayer(me);

		network.sendMessage(Protocol.CLIENT_JOINREQUEST + Protocol.DELIM + getMe().getName()
				+ Protocol.DELIM + "0 0 0 0");
	}

	public void startGame(String opponent) {
		game.addPlayer(new ComputerPlayer(new SmartStrategy(), opponent));
		game.getPlayers().get(0).setBead(new Bead(Colour.RED));
		game.getPlayers().get(1).setBead(new Bead(Colour.YELLOW));
		game.start();
	}

	public void notifyMove(String sx, String sz, String player) throws BadMoveException {
		try {
			int x = Integer.parseInt(sx);
			int z = Integer.parseInt(sz);
			game.doMove(x, z, game.getPlayer(player).getBead());
			addMessage(game.getBoard().toGrid());
		} catch (NumberFormatException e) {
			addMessage("Error parsing move");
		}
	}

	public void askMove() {
		int[] move = new int[2];
		do {
			move = game.getCurrent().determineMove(game.getBoard());
			game.tryMove(move[0], move[1], me.getBead());
		} while (!game.tryMove(move[0], move[1], me.getBead()));

		network.sendMessage(
				Protocol.CLIENT_SETMOVE + Protocol.DELIM + move[0] + Protocol.DELIM + move[1]);
	}

	public void askStartAgain() {
		if (view.askStartAgain()) {
			game.reset();
			game.addPlayer(me);
			network.sendMessage(Protocol.CLIENT_GAMEREQUEST);
		} else {
			shutdown();
		}
	}

	public void shutdown() {
		network.shutdown();
	}

	public Player getMe() {
		return me;
	}

	/**
	 * Start an instance of the Connect 4 3D Client.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {

		ClientController controller = new ClientController();
		controller.addMessage(
				"Connect Four 3D Client by Victor Lap & Niek Khasuntsev " + "\u00a9 2017\n");

		controller.start();
	}

}
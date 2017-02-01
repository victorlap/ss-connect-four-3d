package utwente.ss.connect.client.controller;

import java.net.InetAddress;

import utwente.ss.connect.client.view.TuiView;
import utwente.ss.connect.common.Protocol;
import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Colour;
import utwente.ss.connect.common.controller.Game;
import utwente.ss.connect.common.exception.BadMoveException;
import utwente.ss.connect.common.model.players.*;
import utwente.ss.connect.common.model.strategies.*;

public class ClientController {

	private Game game;

	private Player me;

	private TuiView view;

	private NetworkController network;

	public ClientController() {
		game = new Game();
		view = new TuiView(this);
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
		// TODO
	}

	public void start() {
		InetAddress address = view.connectServer();
		me = new ComputerPlayer(new SmartStrategy());
		me.setName(me.getName());

		game.addPlayer(me);

		network = new NetworkController(address, this);
		network.start();
	}

	public void usernameInUse() {
		game.removePlayer(me);

		me.setName(view.getPlayername());

		game.addPlayer(me);
		
		network.sendMessage(Protocol.CLIENT_JOINREQUEST + Protocol.DELIM + getMe().getName() + Protocol.DELIM + "0 0 0 0");
	}

	public void startGame(String opponent) {
		game.addPlayer(new ComputerPlayer(new NaiveStrategy()));
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

	public void askMove() throws BadMoveException {
		int[] move = view.askMove();
		game.doMove(move[0], move[1], me.getBead());
		network.sendMessage(Protocol.CLIENT_SETMOVE + Protocol.DELIM + move[0] + Protocol.DELIM + move[1]);
	}

	public void askStartAgain() {
		if (view.askStartAgain()) {
			game.reset();
			game.addPlayer(me);
			network.sendMessage(
					Protocol.CLIENT_JOINREQUEST + Protocol.DELIM + me.getName() + Protocol.DELIM + "0 0 0 0");
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
		controller.addMessage("Connect Four 3D Client by Victor Lap & Niek Khasuntsev " + "\u00a9 2017\n");

		controller.start();
	}

}

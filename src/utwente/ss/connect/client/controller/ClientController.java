package utwente.ss.connect.client.controller;

import java.net.InetAddress;

import utwente.ss.connect.client.view.TuiView;
import utwente.ss.connect.common.model.Game;
import utwente.ss.connect.common.model.Player;

public class ClientController {
	
	private Game game;
	
	private String name;
	
	private TuiView view;
	
	private NetworkController network;
	
	public ClientController() {
		game = new Game();
		view = new TuiView(this);
	}
	
	/**
	 * Add a message to the UI.
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
		name = view.getPlayername();
		
		game.addPlayer(new Player(name));
		
		network = new NetworkController(address, this);
		network.start();
	}
	
	public void usernameInUse() {
		game.removePlayer(name);
		
		name = view.getPlayername();
				
		game.addPlayer(new Player(name));
	}
	
	/**
	 * Start an instance of the Connect 4 3D Client.
	 * @param args
	 */
	public static void main(String[] args) {		
		
		ClientController controller = new ClientController();
		controller.addMessage("Connect Four 3D Client by Victor Lap & Niek Khasuntsev " +
							  "\u00a9 2017\n");
		
		controller.start();
	}

}

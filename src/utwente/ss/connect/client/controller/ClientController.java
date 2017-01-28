package utwente.ss.connect.client.controller;

import utwente.ss.connect.common.model.Game;

public class ClientController {
	
	private Game game;
	
	private NetworkController network;
	
	public ClientController() {
		game = new Game();
	}
	
	public void addMessage(String msg) {
		System.out.println(msg);
	}
	
	public void deadConnection() {
		// TODO
	}
	
	public static void main(String[] args) {		
		
		ClientController controller = new ClientController();
		controller.addMessage("Connect Four 3D Client by Victor Lap & Niek Khasuntsev\n" +
							  "\u00a9 2017\n");
	}

}

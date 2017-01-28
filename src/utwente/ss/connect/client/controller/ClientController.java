package utwente.ss.connect.client.controller;

import utwente.ss.connect.common.model.Game;

public class ClientController {
	
	private Game game;
	
	public ClientController() {
		game = new Game();
	}
	
	public void addMessage(String msg) {
		System.out.println(msg);
	}
	
	public static void main(String[] args) {		
		
		ClientController controller = new ClientController();
		controller.addMessage("Connect Four 3D Client by Victor Lap & Niek Khasuntsev\n" +
							  "&copy 2016\n");
				
	}

}

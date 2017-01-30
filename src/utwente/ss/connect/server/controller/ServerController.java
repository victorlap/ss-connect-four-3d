package utwente.ss.connect.server.controller;

public class ServerController {
	
	private NetworkController network;
	
	public ServerController() {
	}
	
	public void addMessage(String msg) {
		System.out.println(msg);
	}
	
	private void startListening() {
		addMessage("Started listening for clients...");
		
		network = new NetworkController(this);
		network.start();
	}
	
	/**
	 * Starts a new instance of the Connect 4 3D Server
	 * @param args
	 */
	public static void main(String[] args) {
		new ServerController();
	}
}

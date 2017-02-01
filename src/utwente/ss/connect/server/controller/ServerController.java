package utwente.ss.connect.server.controller;

import utwente.ss.connect.server.view.TuiView;

public class ServerController {

	private NetworkController network;

	private TuiView view;

	public ServerController() {
		view = new TuiView(this);
	}

	public void addMessage(String msg) {
		System.out.println(msg);
	}

	private void startListening(int port) {
		addMessage("Started listening for clients...");

		network = new NetworkController(this, port);
		network.start();
	}

	public void start() {
		int port = view.getPort();

		startListening(port);
	}



	/**
	 * Starts a new instance of the Connect 4 3D Server.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		ServerController controller = new ServerController();
		controller.start();
	}
}

//192.168.0.175
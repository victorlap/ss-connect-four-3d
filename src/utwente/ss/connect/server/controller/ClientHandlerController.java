package utwente.ss.connect.server.controller;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.net.Socket;

import utwente.ss.connect.common.model.Player;

public class ClientHandlerController extends Thread {
	
	private NetworkController network;
	private ServerController server;
	
	private Socket sock;
	private BufferedReader in;
	private BufferedWriter out;
	
	private Player player;
	
	private boolean isRunning;
	
	public ClientHandlerController(NetworkController network, Socket sock, ServerController server) {
		this.network = network;
		this.sock = sock;
		this.server = server;
		
		this.player = new Player();
	}
	
	public void run() {
		try {		
			in = new BufferedReader(new InputStreamReader(sock.getInputStream()));
			out = new BufferedWriter(new OutputStreamWriter(sock.getOutputStream()));
			
			isRunning = true;

			while(isRunning) {
				if(in.ready()) {
					String command = in.readLine();
					if(command != null && ! command.isEmpty()) {
						network.execute(command, this);
					} else {
						shutdown();
					}
				}
			}
		} catch (IOException e) {
			shutdown();
		}
	}
	
	public void sendMessage(String msg) {
		if(msg != null) {
			try {
				out.write(msg + "\n");
				out.flush();
			} catch (IOException e) {
				server.addMessage("[ERROR] Couldn't send message: "+ msg);
			}
		}
	}
	
	public void shutdown() {
		network.removeHandler(this);
		isRunning = false;
		try {
			in.close();
			out.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
	
	public Player getPlayer() {
		return player;
	}

}
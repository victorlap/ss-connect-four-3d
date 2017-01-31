package utwente.ss.connect.common.model;

import java.util.ArrayList;

public class Game {
	
	public Board board;
	public ArrayList<Player> players;
	
	public boolean hasStarted;
	
	public Game() {
		board = new Board();
		players = new ArrayList<Player>();
	}
	
	public Board getBoard() {
		return board;
	}
	
	public void addPlayer(Player player) {
		players.add(player);
	}
	
	public void removePlayer(Player player) {
		players.remove(player);
	}
	
	public void removePlayer(String playerName) {
		for (Player player : players) {
			if(player.getName().equals(playerName)) {
				players.remove(player);
			}
		}
	}
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void doMove(int x, int z, Bead bead) {
		board.doMove(z, x, bead);
	}
	
	public boolean hasEnded() {
		return board.hasWinner();
	}
	
	public void start() {
		hasStarted = true;
	}
	
	public String getPlayerString() {
		return players.get(0).getName() + " "+ players.get(1).getName();
	}
	
}

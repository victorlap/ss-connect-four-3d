package utwente.ss.connect.common.model;

import java.util.ArrayList;

public class Game {
	
	public Board board;
	public ArrayList<Player> players;
	
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
	
	public ArrayList<Player> getPlayers() {
		return players;
	}
	
	public void doMove(int x, int z, Bead bead) {
		board.doMove(z, x, bead);
	}
	
	public boolean hasEnded() {
		return board.hasWinner();
	}
	
}

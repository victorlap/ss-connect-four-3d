package utwente.ss.connect.common.model;

import java.util.ArrayList;
import java.util.Observable;

public class Game extends Observable {

	public Board board;
	public ArrayList<Player> players;

	public boolean hasStarted;

	/*
	 * @ private invariant 0 <= current && current < NUMBER_PLAYERS;
	 */
	/**
	 * Index of the current player.
	 */
	private int current;

	private String lastmove;

	public Game() {
		board = new Board();
		players = new ArrayList<Player>();
		current = 0;
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
			if (player.getName().equals(playerName)) {
				players.remove(player);
			}
		}
	}

	public ArrayList<Player> getPlayers() {
		return players;
	}

	public void doMove(int x, int z, Bead bead) {
		int y = board.doMove(z, x, bead);
		lastmove = players.get(current).getName() + " " + x + " " + y + " " + z;
		current = (current + 1) % players.size();
		setChanged();
		notifyObservers();
	}

	public boolean isTurn(Player player) {
		return players.indexOf(player) == current;
	}

	public Player getCurrent() {
		return players.get(current);
	}

	public boolean hasEnded() {
		return board.gameOver();
	}

	public void start() {
		hasStarted = true;
	}

	public String getPlayerString() {
		return players.get(0).getName() + " " + players.get(1).getName();
	}

	public String getLastMoveString() {
		return lastmove;
	}

	public boolean hasWinner() {
		return board.hasWinner();
	}

	public Player getWinner() {
		if (board.isWinner(players.get(0).getBead())) {
			return players.get(0);
		}
		return players.get(1);
	}

}

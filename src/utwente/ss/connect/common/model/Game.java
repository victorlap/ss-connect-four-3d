package utwente.ss.connect.common.model;

import java.util.ArrayList;
import java.util.Observable;

import utwente.ss.connect.common.exception.BadMoveException;
import utwente.ss.connect.common.model.players.Player;

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

	public void doMove(int x, int z, Bead bead) throws BadMoveException {
		int y = board.fallToPlace(x, z);
		board.doMove(x, z, bead);
		lastmove = players.get(current).getName() + " " + x + " " + y + " " + z;
		current = (current + 1) % players.size();
		setChanged();
		notifyObservers();
	}

	public boolean tryMove(int x, int z, Bead bead) {
		try {
			board.fallToPlace(x, z);
			return true;
		} catch (BadMoveException e) {
			return false;
		}
	}

	public boolean isTurn(Player player) {
		return players.indexOf(player) == current;
	}

	public boolean hasEnded() {
		return board.gameOver();
	}

	public void start() {
		hasStarted = true;
	}

	public void reset() {
		current = 0;
		players = new ArrayList<Player>();
		board = new Board();
	}

	public Player getCurrent() {
		return players.get(current);
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

	public Player getPlayer(String name) {
		for (Player player : players) {
			if (player.getName().equals(name)) {
				return player;
			}
		}
		return null;
	}

}
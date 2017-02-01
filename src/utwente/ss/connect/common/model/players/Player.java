package utwente.ss.connect.common.model.players;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.strategies.Strategy;

public class Player {

	protected String name;
	protected Bead bead;
	protected int thinkingTime;

	public Player() {
		this.name = "unknown";
	}

	public Player(String name) {
		this.name = name;

	}

	public Player(String name, Bead bead) {
		this.name = name;
		this.bead = bead;
	}

	public Player(Strategy strategy, Bead bead) {
		name = strategy.getName();
		this.bead = bead;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getName() {
		return name;
	}

	public void setBead(Bead bead) {
		this.bead = bead;
	}

	//@pure
	public Bead getBead() {
		return bead;
	}

	@Override
	public String toString() {
		return getName();
	}

	@Override
	public boolean equals(Object o) {
		if (o instanceof Player) {
			Player other = (Player) o;
			return this.getName().equals(other.getName());
		}
		return false;
	}

	public int[] determineMove(Board board) {
		return null;
	}

	public void makeMove(Board board) {
		int[] keuze = determineMove(board);
		board.doMove(keuze[0], keuze[1], getBead());
	}
	
	public void setThinkingTime(int seconds) {
		this.thinkingTime = seconds;
	}
	
	public void think() {
		try {
			Thread.sleep(thinkingTime * 1000);
		} catch (InterruptedException e) {
			// Proceed
		}
	}
}

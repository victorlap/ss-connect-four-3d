package utwente.ss.connect.common.model.players;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.strategies.Strategy;

public class ComputerPlayer extends Player {

	private Strategy strat;

	public ComputerPlayer(Strategy strat) {
		super(strat.getName());
		System.out.println(strat.getName());
		this.strat = strat;
	}

	public ComputerPlayer(Strategy strat, String name) {
		this.name = name;
		this.strat = strat;
	}

	public ComputerPlayer(Strategy strat, Bead bead) {
		super(strat.getName(), bead);
		this.strat = strat;
	}

	@Override
	public String getName() {
		return name;
	}

	@Override
	public int[] determineMove(Board board) {
		think();
		return strat.generateMove(getBead());

	}

}

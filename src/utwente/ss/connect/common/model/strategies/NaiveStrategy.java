package utwente.ss.connect.common.model.strategies;

import utwente.ss.connect.common.model.Bead;

public class NaiveStrategy extends Strategy {

	public NaiveStrategy() {
		super();

	}

	@Override
	public int[] generateMove(Bead bead) {
		return randomMove();
	}

	@Override
	public String getName() {
		return "naive";
	}

}

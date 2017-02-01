package utwente.ss.connect.common.model.strategies;

import java.util.ArrayList;
import java.util.List;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.Colour;

public abstract class Strategy {

	private Board board;

	public Strategy() {
		board = new Board();
	}

	public Board getBoard() {
		return board;
	}
	
	public abstract String getName();

	public abstract int[] generateMove(Bead bead);

	public int[] randomMove() {
		List<int[]> moves = getMoves(getBoard());
		return moves.get((int) (Math.random() * moves.size()));
	}

	public List<int[]> getMoves(Board strategyBoard) {
		List<int[]> allMoves = new ArrayList<int[]>();
		for (int x = 0; x < strategyBoard.getDIM(); x++) {
			for (int z = 0; z < strategyBoard.getDIM(); z++) {
				if (strategyBoard.getField(x, z, strategyBoard.getDIM() - 1)
						.toString().equals(Colour.EMPTY)) {
					int[] move = new int[2];
					move[0] = x;
					move[1] = z;
					allMoves.add(move);
				}
			}
		}
		return allMoves;
	}

}

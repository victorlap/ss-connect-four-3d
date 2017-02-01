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
		List<int[]> moves = getPossibleMoves(getBoard());
		return moves.get((int) (Math.random() * moves.size()));
	}

	public List<int[]> getPossibleMoves(Board temp) {
		List<int[]> myList = new ArrayList<int[]>();
		for (int c = 0; c < temp.getDIM(); c++) {
			for (int r = 0; r < temp.getDIM(); r++) {
				
				if (temp.getField(c, r, temp.getDIM() - 1).getColour().equals(Colour.EMPTY)) {
					int[] move = new int[2];
					move[0] = c;
					move[1] = r;
					myList.add(move);
				}
			}
		}
		return myList;
	}

}

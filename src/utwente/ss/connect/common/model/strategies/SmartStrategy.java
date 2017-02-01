package utwente.ss.connect.common.model.strategies;

import java.util.ArrayList;
import java.util.List;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;

public class SmartStrategy extends Strategy {
	public SmartStrategy() {
		super();
	}

	private Bead nextBead;
	
	@Override
	public String getName() {
		// TODO Auto-generated method stub
		return "Smart";
	}

	@Override
	public int[] generateMove(Bead bead) {
		nextBead = new Bead(bead.next(bead));
//		try {
//			Thread.sleep(2000);
//		} catch (InterruptedException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		List<int[]> myList = getPossibleMoves(getBoard());
		Board board = getBoard();
		
		// check if the enemy has a winning move
		for (int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.doMove(myList.get(i)[0], myList.get(i)[1], nextBead);
			if (temp.isWinner(nextBead)) {
				return myList.get(i);
			}
		}
		
		// check if you have a winning move
		for (int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.doMove(myList.get(i)[0], myList.get(i)[1], bead);
			if (temp.isWinner(bead)) {
				return myList.get(i);
			}
		}
		// for all possible moves, check if the move you're going to make
		// creates a win condition for the enemy
		List<int[]> safeMoves = new ArrayList<int[]>();
		for (int i = 0; i < myList.size(); i++) {
			Board temp = board.deepCopy();
			temp.doMove(myList.get(i)[0], myList.get(i)[1], bead);
			List<int[]> futureList = getPossibleMoves(temp);
			boolean valid = true;
			for (int j = 0; j < futureList.size(); j++) {
				temp.doMove(futureList.get(j)[0], futureList.get(j)[1], nextBead);
				if (temp.isWinner(nextBead)) {
					valid = false;
				}
			}
			if (valid) {
				safeMoves.add(myList.get(i));
			}
		}
		if (safeMoves.size() < 1) {
			return randomMove();
		} else {
			return safeMoves.get((int) (Math.random() * safeMoves.size()));
		}
	}
}

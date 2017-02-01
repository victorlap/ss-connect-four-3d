package utwente.ss.connect.test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utwente.ss.connect.common.exception.BadMoveException;
import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.Colour;

public class BoardTest {

	Board board;

	private Bead yellowBead = new Bead(Colour.YELLOW);
	private Bead redBead = new Bead(Colour.RED);

	@Before
	public void setUp() throws Exception {
		board = new Board();
	}

	@Test
	public void emptyBoard() {
		assertFalse(board.isFull());
	}

	/**
	 * Test whether an empty space on the board is really a Bead with Colour
	 * "empty".
	 */
	@Test
	public void emptyField() {
		assertTrue(board.getField(0, 0, 0).getColour().equals(Colour.EMPTY));
		assertTrue(board.isEmptyField(0, 0, 0));
	}

	@Test
	public void doMoveTest() {
		board.doMove(0, 0, yellowBead);
		assertEquals(board.getField(0, 0, 0), yellowBead);
		board.reset();
	}

	@Test
	public void emptyBoard2() {
		assertFalse(board.isFull());
	}

	@Test
	public void fullTtest() {
		assertFalse(board.isFull());
	}

	@Test
	public void fallTest() throws BadMoveException {
		board.doMove(0, 0, yellowBead);
		assertTrue(board.fallToPlace(0, 0) == 1);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.fallToPlace(0, 0) == 2);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.fallToPlace(0, 0) == 3);
		board.doMove(0, 0, yellowBead);

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void testHasCol() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.hasColumn(yellowBead));
		assertTrue(board.hasWinner());

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void testHasRow() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 0, yellowBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertTrue(board.hasRow(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void testHasNoRow() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 0, redBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertFalse(board.hasRow(yellowBead));

		assertFalse(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertFalse(board.hasWinner());
	}

	@Test
	public void testHasDepth() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 1, yellowBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 3, yellowBead);
		assertTrue(board.hasDepth(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void testHasNoDepth() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 1, redBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 3, yellowBead);
		assertFalse(board.hasDepth(yellowBead));
		assertFalse(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertFalse(board.hasWinner());
	}

	@Test
	public void testHasDiagonalXY() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 0, redBead);
		board.doMove(1, 0, yellowBead);
		board.doMove(2, 0, redBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(3, 0, redBead);
		board.doMove(3, 0, redBead);
		board.doMove(3, 0, yellowBead);
		board.doMove(3, 0, yellowBead);

		assertTrue(board.hasDiagonalXY(yellowBead));
		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void testHasOtherDiagonalXY() {
		board.doMove(3, 0, yellowBead);
		board.doMove(2, 0, redBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(1, 0, redBead);
		board.doMove(1, 0, yellowBead);
		board.doMove(1, 0, yellowBead);
		board.doMove(0, 0, redBead);
		board.doMove(0, 0, redBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);

		assertTrue(board.hasDiagonalXY(yellowBead));
		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());

	}

	@Test
	public void hasDiagonalXZ() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(2, 2, yellowBead);
		board.doMove(3, 3, yellowBead);
		assertTrue(board.hasDiagonalXZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void hasOtherDiagonalXZ() {
		board.doMove(0, 3, yellowBead);
		board.doMove(1, 2, yellowBead);
		board.doMove(2, 1, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertTrue(board.hasDiagonalXZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void hasDiagonalYZ() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 1, redBead);
		board.doMove(0, 1, yellowBead);
		board.doMove(0, 2, redBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 3, yellowBead);
		board.doMove(0, 3, redBead);
		board.doMove(0, 3, redBead);
		board.doMove(0, 3, yellowBead);
		assertTrue(board.hasDiagonalYZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());

	}

	@Test
	public void hasOtherDiagonalYZ() {
		board.doMove(0, 3, yellowBead);
		board.doMove(0, 2, redBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 1, redBead);
		board.doMove(0, 1, yellowBead);
		board.doMove(0, 1, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, redBead);
		board.doMove(0, 0, redBead);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.hasDiagonalYZ(yellowBead));
	}

	@Test
	public void bottomLeftToTopRight() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(2, 2, redBead);
		board.doMove(2, 2, redBead);
		board.doMove(2, 2, yellowBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, yellowBead);
		assertTrue(board.hasDiagXYZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void bottomRightToTopLeft() {
		board.doMove(3, 0, yellowBead);
		board.doMove(2, 1, yellowBead);
		board.doMove(2, 1, yellowBead);
		board.doMove(1, 2, redBead);
		board.doMove(1, 2, redBead);
		board.doMove(1, 2, yellowBead);
		board.doMove(0, 3, redBead);
		board.doMove(0, 3, redBead);
		board.doMove(0, 3, redBead);
		board.doMove(0, 3, yellowBead);
		assertTrue(board.hasDiagXYZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void otherBottomRightToTopLeft() {
		board.doMove(0, 3, yellowBead);
		board.doMove(1, 2, yellowBead);
		board.doMove(1, 2, yellowBead);
		board.doMove(2, 1, redBead);
		board.doMove(2, 1, redBead);
		board.doMove(2, 1, yellowBead);
		board.doMove(3, 0, redBead);
		board.doMove(3, 0, redBead);
		board.doMove(3, 0, redBead);
		board.doMove(3, 0, yellowBead);
		assertTrue(board.hasDiagXYZ(yellowBead));

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void NoBottomLeftToTopRight() {
		board.doMove(0, 0, redBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(2, 2, redBead);
		board.doMove(2, 2, redBead);
		board.doMove(2, 2, yellowBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, redBead);
		board.doMove(3, 3, yellowBead);
		assertFalse(board.hasDiagXYZ(yellowBead));
		assertFalse(board.hasWinner());

		assertFalse(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertFalse(board.hasWinner());
	}

	@Test
	public void toStringTest() {
		board.doMove(0, 0, redBead);
		assertTrue(board.getField(0, 0, 0).toString().equals("\u001B[31m" + "\u2B24" + "\u001B[0m"));
	}

	@Test
	public void gameOverWinnerTest() {
		assertFalse(board.gameOver());
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.gameOver());

		assertTrue(board.isWinner(yellowBead));
		assertFalse(board.isWinner(redBead));
		assertTrue(board.hasWinner());
	}

	@Test
	public void gameOverFullTest() {
		assertFalse(board.gameOver());
		for (int x = 0; x < board.getDIM(); x++) {
			for (int y = 0; y < board.getDIM(); y++) {
				for (int z = 0; z < board.getDIM(); z++) {
					board.doMove(x, z, redBead);
				}
			}
		}
		assertTrue(board.gameOver());
	}

	@Test
	public void deepCopyTest(){
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		
		Board temp = board.deepCopy();
		
		boolean equal = true;
		for (int x = 0; x < board.getDIM(); x++) {
			for (int y = 0; y < board.getDIM(); y++) {
				for (int z = 0; z < board.getDIM(); z++) {
					if(!board.getField(x, y, z).equals(temp.getField(x, y, z))){
						equal = false;
					}
				}
			} 
			assertTrue(equal);
		}
	}

	@Test
	public void testToGrid() {
		board.toGrid();
	}

}

package utwente.ss.connect.test;

import static org.junit.Assert.*;

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
	}

	@Test
	public void testHasCol() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 0, yellowBead);
		assertTrue(board.hasColumn(yellowBead));
	}

	@Test
	public void testHasRow() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 0, yellowBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertTrue(board.hasRow(yellowBead));
	}

	@Test
	public void testHasNoRow() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 0, redBead);
		board.doMove(2, 0, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertFalse(board.hasRow(yellowBead));
	}

	@Test
	public void testHasDepth() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 1, yellowBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 3, yellowBead);
		assertTrue(board.hasDepth(yellowBead));
	}

	@Test
	public void testHasNoDepth() {
		board.doMove(0, 0, yellowBead);
		board.doMove(0, 1, redBead);
		board.doMove(0, 2, yellowBead);
		board.doMove(0, 3, yellowBead);
		assertFalse(board.hasDepth(yellowBead));
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
		
		System.out.print(board.deepCopy().toGrid());
	}

	@Test
	public void hasDiagonalXZ() {
		board.doMove(0, 0, yellowBead);
		board.doMove(1, 1, yellowBead);
		board.doMove(2, 2, yellowBead);
		board.doMove(3, 3, yellowBead);
		assertTrue(board.hasDiagonalXZ(yellowBead));
	}

	@Test
	public void hasOtherDiagonalXZ() {
		board.doMove(0, 3, yellowBead);
		board.doMove(1, 2, yellowBead);
		board.doMove(2, 1, yellowBead);
		board.doMove(3, 0, yellowBead);
		assertTrue(board.hasDiagonalXZ(yellowBead));
	}
	
	@Test
	public void hasDiagonalYZ(){
		
	}

}

package utwente.ss.connect.test;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.Colour;

public class BoardTest {

	Board b;

	@Before
	public void setUp() throws Exception {
		b = new Board();
		b.doMove(0, 0, new Bead(Colour.RED));
		b.doMove(1, 0, new Bead(Colour.RED));
		b.doMove(2, 0, new Bead(Colour.RED));
		b.doMove(3, 0, new Bead(Colour.RED));
		b.doMove(0, 0, new Bead(Colour.RED));
		b.doMove(0, 0, new Bead(Colour.RED));
		b.doMove(0, 0, new Bead(Colour.RED));

		b.doMove(0, 1, new Bead(Colour.RED));
		b.doMove(0, 2, new Bead(Colour.RED));
		b.doMove(0, 3, new Bead(Colour.RED));

		b.doMove(1, 0, new Bead(Colour.RED));
		b.doMove(2, 0, new Bead(Colour.RED));
		b.doMove(2, 0, new Bead(Colour.RED));
		b.doMove(1, 0, new Bead(Colour.RED));
		b.doMove(3, 0, new Bead(Colour.RED));
		b.doMove(3, 0, new Bead(Colour.RED));
		// b.doMove(3, 0, new Bead(Colour.RED));

		System.out.println(b.toGrid());
	}

	@Test
	public void emptyBoard() {
		assertFalse(b.isFull());
	}

	@Test
	public void rules() {
		// assertFalse(b.has)
	}

	@Test
	public void testHasRow() {
		assertTrue(b.hasRow(new Bead(Colour.RED)));
	}

	@Test
	public void testHasCol() {
		assertTrue(b.hasColumn(new Bead(Colour.RED)));
	}

	@Test
	public void testHasDepth() {
		assertTrue(b.hasDepth(new Bead(Colour.RED)));
	}

	@Test
	public void testHasDiagonalXY() {
		assertTrue(b.hasDiagonalXY(new Bead(Colour.RED)));
	}

}

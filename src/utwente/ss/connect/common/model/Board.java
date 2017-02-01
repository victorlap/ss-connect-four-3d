package utwente.ss.connect.common.model;

import utwente.ss.connect.common.exception.BadMoveException;

public class Board {

	/**
	 * DIMensions of the board.
	 */
	private static final int DIM = 4;

	/**
	 * A board is actually a three Dimensional bead object array.
	 */
	private Bead[][][] board;

	public static Bead emptybead = new Bead(Colour.EMPTY);

	// --- Constructor ---

	/*
	 * @ invariant (\forall int x, y, z; 0 <= x & x < getDIM() & 0 <= y & y <
	 * getDIM() & 0 <= z & z < getDIM(); getField(x, y, z) == Bead(Colour.Empty)
	 * || getField(x, y, z) == Bead(Colour.Red) || getField(x, y, z) ==
	 * Bead(Colour.Yellow));
	 */

	/*
	 * @ ensures (\forall int x, y, z; 0 <= x & x < getDIM() & 0 <= y & y <
	 * getDIM() & 0 <= z & z < getDIM(); getField(x, y, z) ==
	 * Bead(Colour.Empty);
	 */
	public Board() {
		board = new Bead[DIM][DIM][DIM];
		reset();
	}

	// --- Commands ---

	public int getDIM() {
		return DIM;
	}

	/*
	 * @ ensures (\forall int x, y, z; 0 <= x & x < getDIM() & x <= y & y <
	 * getDIM() & 0 <= z & z < getDIM(); getField (x, y, z) ==
	 * Bead(Colour.EMPTY);
	 */
	public void reset() {
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				for (int z = 0; z < DIM; z++) {
					this.board[x][y][z] = emptybead;
				}
			}
		}
	}

	/**
	 * 
	 * @param x
	 *            coordinate
	 * @param y
	 *            coordinate
	 * @param z
	 *            coordinate
	 * @return Bead object on the place in the array
	 */

	/*
	 * @requires x >= 0 & x < getDIM(); && y >= 0 & y < getDIM() && z >= 0 & z <
	 * getDIM();
	 * 
	 * @pure
	 */
	public Bead getField(int x, int y, int z) {
		return board[x][y][z];
	}

	/**
	 * User provides X and Y coordinate and the system calculates how far the.
	 * bead can fall and places the bead on the board
	 */

	/*
	 * @requires x >= 0 & x < getDIM() && z >= 0 & z < getDIM();
	 * 
	 * @ensures (\exists int y; y >= 0 & y < getDIM(); getField(x, y, z) ==
	 * bead);
	 */
	public void doMove(int x, int z, Bead bead) {
		try {
			int y = fallToPlace(x, z);
			board[x][y][z] = bead;
		} catch (BadMoveException e) {
			System.out.println("collumn full, try another collumn");
		}
	}

	/**
	 * Gravity method
	 * 
	 * Calculate how far the bead can fall (calculate what the lowest Y value
	 * is) in the collumn.
	 * 
	 * @throws BadMoveException
	 */

	/*
	 * @requires x >= 0 & x < getDIM() && z >= 0 & z < getDIM();
	 * 
	 * @ensures (\exists int y; y >= 0 & y < getDIM(); getField(x, y, z) ==
	 * bead);
	 */
	public int fallToPlace(int x, int z) throws BadMoveException {
		int y = 0;
		while (y < DIM) {
			if (board[x][y][z].getColour().equals(Colour.EMPTY)) {
				return y;
			} else {
				y++;
			}
		}
		throw new BadMoveException();
	}

	/**
	 * Checks whether the board is full.
	 */

	/*
	 * @ ensures (\forall int x, y, z; 0 <= x & x < getDIM() & x <= y & y <
	 * getDIM() & 0 <= z & z < getDIM(); getField (x, y, z) !=
	 * Bead(Colour.EMPTY);
	 */
	public boolean isFull() {
		boolean full = true;
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				for (int z = 0; z < DIM; z++) {
					if (board[x][y][z].getColour().equals(Colour.EMPTY)) {
						full = false;
					}
				}
			}
		}
		return full;
	}

	/*
	 * @ ensures (int x, y, z; 0 <= x & x < getDIM() & x <= y & y < getDIM() & 0
	 * <= z & z < getDIM(); getField (x, y, z) == Bead(Colour.EMPTY);
	 */
	public boolean isEmptyField(int x, int y, int z) {
		if (board[x][y][z].getColour().equals(Colour.EMPTY)) {
			return true;
		}
		return false;
	}

	// --- Rule methods ---

	/**
	 * Checks whether the given bead has a row.
	 * 
	 * @param bead
	 * @return boolean
	 */
	/*
	 * @ensures \result == (\exists int x, z; x >= 0 & z >= 0 & x < getDIM() & z
	 * < getDIM(); (\forall int y; y >= 0 & y < getDIM(); getField(x, y , z) ==
	 * bead;
	 * 
	 */
	public boolean hasRow(Bead bead) {
		boolean full;
		for (int y = 0; y < DIM; y++) {
			for (int z = 0; z < DIM; z++) {
				full = true;
				for (int x = 0; x < DIM; x++) {
					if (!board[x][y][z].getColour().equals(bead.getColour())) {
						full = false;
					}
				}
				if (full) {
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a column.
	 * 
	 * @param bead
	 * @return boolean
	 */

	/*
	 * @ensures \result == (\exists int y, z; y >= 0 & h >= 0 & y < getDIM() & z
	 * < getDIM(); (\forall int x; x >= 0 & x < getDIM(); getField(x, y, z) ==
	 * bead));
	 * 
	 * @pure
	 */
	public boolean hasColumn(Bead bead) {
		boolean full;
		for (int x = 0; x < DIM; x++) {
			for (int z = 0; z < DIM; z++) {
				full = true;
				for (int y = 0; y < DIM; y++) {
					if (!board[x][y][z].getColour().equals(bead.getColour())) {
						full = false;
					}
				}
				if (full) {
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a row in depth (Z axis).
	 * 
	 * @param bead
	 * @return boolean
	 */
	/*
	 * @ensures \result == (\exists int r, c; r >= 0 & c >= 0 & r < getDIM() & c
	 * * < getDIM(); (\forall int h; h >= 0 & h < getDIM(); getField(c, r, h) ==
	 * * m));
	 * 
	 * @pure
	 */
	public boolean hasDepth(Bead bead) {
		boolean full;
		for (int y = 0; y < DIM; y++) {
			for (int x = 0; x < DIM; x++) {
				full = true;
				for (int z = 0; z < DIM; z++) {
					if (!board[x][y][z].getColour().equals(bead.getColour())) {
						full = false;
					}
				}
				if (full) {
					return true;

				}
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a diagonal in the XY plane.
	 */
	public boolean hasDiagonalXY(Bead bead) {
		boolean full;
		for (int z = 0; z < DIM; z++) {
			full = true;
			for (int xy = 0; xy < DIM; xy++) {
				if (!board[xy][xy][z].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int z = 0; z < DIM; z++) {
			full = true;
			for (int xy = 0; xy < DIM; xy++) {
				if (!board[xy][DIM - xy - 1][z].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a winning sequence through XZ.
	 */
	public boolean hasDiagonalXZ(Bead bead) {
		boolean full;
		for (int y = 0; y < DIM; y++) {
			full = true;
			for (int xz = 0; xz < DIM; xz++) {
				if (!board[xz][y][xz].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int y = 0; y < DIM; y++) {
			full = true;
			for (int xz = 0; xz < DIM; xz++) {
				if (!board[xz][y][DIM - xz - 1].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a winning sequence through YZ.
	 */
	public boolean hasDiagonalYZ(Bead bead) {
		boolean full;
		for (int x = 0; x < DIM; x++) {
			full = true;
			for (int yz = 0; yz < DIM; yz++) {
				if (!board[x][yz][yz].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int x = 0; x < DIM; x++) {
			full = true;
			for (int yz = 0; yz < DIM; yz++) {
				if (!board[x][yz][DIM - yz - 1].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks whether the given bead forms a diagonal through XYZ.
	 */
	public boolean hasDiagXYZ(Bead bead) {
		// Four different starting points
		boolean diagonalTopLeft = true;
		boolean diagonalTopRight = true;
		boolean diagonalBottomLeft = true;
		boolean diagonalBottomRight = true;
		for (int xyz = 0; xyz < DIM; xyz++) {
			if (!board[xyz][xyz][xyz].getColour().equals(bead.getColour())) {
				diagonalTopLeft = false;
			}
			if (!board[DIM - xyz - 1][xyz][xyz].getColour().equals(bead.getColour())) {
				diagonalTopRight = false;
			}
			if (!board[xyz][DIM - xyz - 1][xyz].getColour().equals(bead.getColour())) {
				diagonalBottomLeft = false;
			}
			if (!board[DIM - xyz - 1][DIM - xyz - 1][xyz].getColour().equals(bead.getColour())) {
				diagonalBottomRight = false;
			}
		}

		return diagonalTopLeft || diagonalTopRight || diagonalBottomLeft || diagonalBottomRight;
	}

	/**
	 * Checks whether the given bead forms a winning sequence.
	 */
	public boolean isWinner(Bead bead) {
		return hasRow(bead) || hasColumn(bead) || hasDepth(bead) || hasDiagonalYZ(bead) || hasDiagonalXY(bead)
				|| hasDiagonalXZ(bead) || hasDiagXYZ(bead);

	}

	/**
	 * Checks for both beads wether there is a winner.
	 */
	public boolean hasWinner() {
		return isWinner(new Bead(Colour.RED)) || isWinner(new Bead(Colour.YELLOW));
	}

	/**
	 * Checks wether the game is over.
	 */
	public boolean gameOver() {
		return isFull() || hasWinner();
	}

	// --- Printing methods ---

	/**
	 * Build a grid out of the board.
	 */
	public String toGrid() {

		StringBuilder builder = new StringBuilder();
		final String newLine = System.getProperty("line.separator");
		final String tab = "   ";

		for (int i = 0; i < DIM; i++) {
			builder.append("   z = " + (i) + "  " + tab + tab + tab + tab);
		}
		builder.append(newLine);

		for (int x = DIM - 1; x >= 0; x--) {
			for (int z = 0; z < DIM; z++) {
				builder.append(x + " ");
				for (int y = 0; y < DIM; y++) {
					builder.append(board[y][x][z].toString());
					if (y != DIM - 1) {
						builder.append(" | ");
					}
				}
				builder.append(tab);
			}
			builder.append(newLine);
		}
		for (int i = 0; i < DIM; i++) {
			builder.append("  0    1    2    3   ");
			if (i != 1) {
				builder.append(" ");
			}
		}
		builder.append(" <= x-axis\n");

		return builder.toString();

	}

	public Board deepCopy() {
		Board copy = new Board();
		for (int x = 0; x < DIM; x++) {
			for (int y = 0; y < DIM; y++) {
				for (int z = 0; z < DIM; z++) {
					copy.board[x][y][z] = this.board[x][y][z];
				}
			}
		}
		return copy;
	}
}

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
	public Board() {
		board = new Bead[DIM][DIM][DIM];
		reset();
	}

	// --- Commands ---

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
	 * @param y
	 * @param z
	 * @return Bead object on the place in the array
	 */
	public Bead getField(int x, int y, int z) {
		return board[x][y][z];
	}

	/**
	 * User provides X and Y coordinate and the system calculates how far the.
	 * bead can fall and places the bead on the board
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

	public boolean isEmptyField(int x, int y, int z) {
		if (board[x][y][z].equals(Colour.EMPTY)) {
			return true;
		}
		return false;
	}

	/**
	 * Checks whether the given bead has a row.
	 * 
	 * @param bead
	 * @return boolean
	 */
	public boolean hasRow(Bead bead) {
		boolean full;
		for (int col = 0; col < DIM; col++) {
			for (int depth = 0; depth < DIM; depth++) {
				full = true;
				for (int row = 0; row < DIM; row++) {
					if (!board[row][col][depth].getColour().equals(bead.getColour())) {
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

	// --- Rule methods ---

	/**
	 * Checks whether the given bead has a column.
	 * 
	 * @param bead
	 * @return boolean
	 */

	public boolean hasColumn(Bead bead) {
		boolean full;
		for (int row = 0; row < DIM; row++) {
			for (int depth = 0; depth < DIM; depth++) {
				full = true;
				for (int col = 0; col < DIM; col++) {
					if (!board[row][col][depth].getColour().equals(bead.getColour())) {
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
		for (int col = 0; col < DIM; col++) {
			for (int row = 0; row < DIM; row++) {
				full = true;
				for (int depth = 0; depth < DIM; depth++) {
					if (!board[row][col][depth].getColour().equals(bead.getColour())) {
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
		for (int depth = 0; depth < DIM; depth++) {
			full = true;
			for (int xy = 0; xy < DIM; xy++) {
				if (!board[xy][xy][depth].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int height = 0; height < DIM; height++) {
			full = true;
			for (int xy = 0; xy < DIM; xy++) {
				if (!board[xy][DIM - xy - 1][height].getColour().equals(bead.getColour())) {
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
		for (int col = 0; col < DIM; col++) {
			full = true;
			for (int xz = 0; xz < DIM; xz++) {
				if (!board[xz][col][xz].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int col = 0; col < DIM; col++) {
			full = true;
			for (int xz = 0; xz < DIM; xz++) {
				if (!board[xz][col][DIM - xz - 1].getColour().equals(bead.getColour())) {
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
		for (int row = 0; row < DIM; row++) {
			full = true;
			for (int yz = 0; yz < DIM; yz++) {
				if (!board[yz][yz][yz].getColour().equals(bead.getColour())) {
					full = false;
				}
			}
			if (full) {
				return true;
			}
		}
		for (int row = 0; row < DIM; row++) {
			full = true;
			for (int yz = 0; yz < DIM; yz++) {
				if (!board[row][yz][DIM - yz - 1].getColour().equals(bead.getColour())) {
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
		return hasRow(bead) || hasColumn(bead) || hasDepth(bead) || hasDiagonalYZ(bead)
				|| hasDiagonalXY(bead) || hasDiagonalXZ(bead) || hasDiagXYZ(bead);

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

	// toString method only used for testing
	public String toString() {
		for (int i = 0; i < DIM; i++) {
			for (int j = 0; j < DIM; j++) {
				for (int k = 0; k < DIM; k++) {
					System.out.println(board[i][j][k]);
				}
			}
		}
		return "succes";
	}

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

	public int getDIM() {
		return DIM;
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

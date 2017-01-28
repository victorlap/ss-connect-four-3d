package utwente.ss.connect.common.model;

import utwente.ss.connect.common.exception.BadMoveException;

public class Board {

	// --- Constructor ---
	
	/**
	 * Dimensions of the board
	 */
	private static final int DIM = 4;

	/**
	 * A board is actually a three dimensional bead object array.
	 */
	Bead[][][] board = new Bead[DIM][DIM][DIM];

	static Bead bead = new Bead(Colour.EMPTY);

	public Board() {
		int x = 0;
		int y = 0;
		int z = 0;

		// Fill every position in the board with an empty bead object
		for (x = 0; x < DIM; x++) {
			for (y = 0; y < DIM; y++) {
				for (z = 0; z < DIM; z++) {
					this.board[x][y][z] = bead;
				}
			}
		}
	}

	// --- Commands ---

	// TODO Implement logic
	public boolean hasWinner() {
		return false;
	}

	/**
	 * User provides X and Y coordinate and the system calculates how far the
	 * bead can fall and places the bead on the board
	 */
	public void doMove(int z, int x, Bead bead) {
		try {
			int y = fallToPlace(x, z);
			board[x][y][z] = bead;
		} catch (Exception e) {
			System.out.println("collumn full, try another collumn");
		}
	}

	/**
	 * Calculate how far the bead can fall (calculate what the lowest Y value
	 * is) in the collumn
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
	 * Build a grid out of the board
	 */
	public String toGrid() {

		StringBuilder builder = new StringBuilder();
		String newLine = System.getProperty("line.separator");

		builder.append(" ");
		builder.append(newLine);
		builder.append("   ");

		for (int z = 0; z < DIM; z++) {
			builder.append("z = " + z);
			builder.append(newLine);
			for (int i = 0; i < DIM; i++) {
				builder.append(i + " | ");
			}
			for (int x = 0; x < DIM; x++) {
				builder.append(newLine);
				builder.append(x + " ");
				for (int y = 0; y < DIM; y++) {
					builder.append(board[y][x][z].toString() + " | ");
				}
			}
			builder.append(newLine);
			builder.append(newLine);
			builder.append(newLine);
		}
		System.out.println(builder.toString());
		return builder.toString();

	}

	public static void main(String[] args) {

		Board b = new Board();

		b.doMove(0, 0, bead = new Bead(Colour.RED));
		b.doMove(2, 0, bead = new Bead(Colour.YELLOW));
		b.doMove(0, 0, bead = new Bead(Colour.YELLOW));
		b.doMove(0, 1, bead = new Bead(Colour.RED));
		b.doMove(0, 2, bead = new Bead(Colour.YELLOW));
		b.doMove(1, 0, bead = new Bead(Colour.YELLOW));
		b.doMove(3, 3, bead = new Bead(Colour.YELLOW));

		b.doMove(3, 0, bead = new Bead(Colour.YELLOW));

		b.toGrid();

		// System.out.println(Arrays.deepToString(board));

	}

}

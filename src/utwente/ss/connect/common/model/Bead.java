package utwente.ss.connect.common.model;

public class Bead {
	// ANSI codes for colour representation in the tui
	public static final String RESET = "\u001B[0m";
	public static final String RED = "\u001B[31m";
	public static final String YELLOW = "\u001B[33m";

	// Used to represent empty spaces on the board
	public static final String ANSI_WHITE = "\u001B[37m";

	// Visual representation of a bead
	public static final String BEAD = "\u2B24";

	private Colour colour;
	private Bead bead;

	public Bead(Colour colour) {
		this.colour = colour;
	}

	public Bead getBead() {
		return bead;
	}

	public Colour getColour() {
		return colour;
	}

	public String toString() {
		return getColour().toString() + BEAD + RESET;
	}

	public Colour next(Bead b) {
		switch (b.getColour()) {
			case YELLOW:
				return Colour.RED;
			case RED:
				return Colour.YELLOW;
			default:
				return Colour.EMPTY;
		}
	}
}

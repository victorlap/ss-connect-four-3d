package view;

import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.Mark;

public class TUI {
	// ANSI codes for colour representation in the tui
	public static final String ANSI_RESET = "\u001B[0m";
	public static final String ANSI_RED = "\u001B[31m";
	public static final String ANSI_YELLOW = "\u001B[33m";

	// Used to represent empty spaces on the board
	public static final String ANSI_WHITE = "\u001B[37m";

	// Visual representation of a bead
	public static final String BEAD = "\u2B24";
	
	private Mark mark;
	
	public Mark getMark() {
		return mark;
	}
	
	public String toString(){
		return "" + mark;
	}
	
	public String toAnsi(){
		if (mark.toString().equals("RED")) {
			return ANSI_RED + BEAD + ANSI_RESET;
		} else if (mark.toString().equals("YELLOW")) {
			return ANSI_YELLOW + BEAD + ANSI_RESET;
		} else {
			return ANSI_WHITE + BEAD + ANSI_RESET;
		}
	}
	
	public String toGrid() {

		final int DIM = 4;
		
		Mark[][][] board = new Mark[DIM][DIM][DIM];
		
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
					builder.append(board[y][x][z].toAnsi() + " | ");
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
		System.out.println(board.toString());
	}
	
	
	
	
	
	
	
	
	
	
	
//	Mark(Colour colour){
//		this.colour = colour;
//	}

//	public String toString() {
//		if (mark.toString().equals("RED")) {
//			return ANSI_RED + BEAD + ANSI_RESET;
//		} else if (mark.toString().equals("YELLOW")) {
//			return ANSI_YELLOW + BEAD + ANSI_RESET;
//		} else {
//			return ANSI_WHITE + BEAD + ANSI_RESET;
//		}
//	}
	

}

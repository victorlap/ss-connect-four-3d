package utwente.ss.connect.common.model;

import java.util.Scanner;

/**
 * @author Niek Khasuntsev
 */
public class HumanPlayer extends Player {

	// -- Constructors -----------------------------------------------

	/**
	 * Creates a new human player object.
	 * 
	 */
	public HumanPlayer(String name, Bead bead) {
		super(name, bead);
	}

	// -- Commands ---------------------------------------------------

	@Override
	public String determineMove(Board board) {
		String prompt = "> " + getName() + " (" + getBead().toString() + ")" + ", give command? ";
		String[] choice = readString(prompt);
		System.out.println(choice.toString());
		boolean valid = board.isField(Integer.valueOf(choice[0]), Integer.valueOf(choice[1]));
		while (!valid) {
			System.out.println("ERROR: field " + choice + " is no valid choice.");
			choice = readString(prompt);
			valid = board.isField(Integer.valueOf(choice[0]), Integer.valueOf(choice[1]));
		}
		return Integer.valueOf(choice[0])+ " " + Integer.valueOf(choice[1]);

	}

	private String[] readString(String prompt) {
		String input = null;
		boolean intRead = false;
		@SuppressWarnings("resource")
		Scanner line = new Scanner(System.in);
		do {
			System.out.print(prompt);
			try (Scanner scannerLine = new Scanner(line.nextLine());) {
				if (scannerLine.hasNextLine()) {
					intRead = true;
					input = scannerLine.next();
				}
			}
		} while (!intRead);
		 return input.split("\\s+");
	}

}

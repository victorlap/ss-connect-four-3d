package utwente.ss.connect.common.model;

import java.util.Scanner;

import utwente.ss.connect.common.exception.BadMoveException;

/**
 * @author Niek Khasuntsev
 */
public class HumanPlayer extends Player {

	private Scanner in;

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
	public int[] determineMove(Board board) {
		int[] choice = new int[2];
		System.out.println(getName() + "(" + getBead() + ")" + ", your turn!");
		in = new Scanner(System.in);
		System.out.println("Do you want a hint? Y/N");
		boolean valid = false;
		while (!valid) {
			try {
				String input = in.nextLine();
				input = input.toLowerCase();
				if (input.equals("n")) {
					valid = true;
				} else if (input.equals("y")) {
					valid = true;
				} else {
					throw new BadMoveException();
				}
			} catch (BadMoveException e) {
				System.out.println(e.getMessage() + " Please write either N or Y");
				valid = false;
			}
		}
		System.out.println("Enter a value for X (between 0 and 3)");
		choice[0] = -1;
		while (choice[0] < 0) {
			try {
				String input = in.nextLine();
				choice[0] = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, please provide a valid X");
				choice[0] = -1;
			}
		}

		System.out.println("Please enter a Z value (between 0 and 3)");
		choice[1] = -1;
		while (choice[1] < 0) {
			try {
				String input = in.nextLine();
				choice[1] = Integer.parseInt(input);
			} catch (NumberFormatException e) {
				System.out.println("Invalid input, please provide a valid Z value");
				choice[1] = -1;
			}
		}
		return choice;
	}

}

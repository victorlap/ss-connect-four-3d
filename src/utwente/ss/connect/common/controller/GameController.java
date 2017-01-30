package utwente.ss.connect.common.controller;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Colour;
import utwente.ss.connect.common.model.HumanPlayer;
import utwente.ss.connect.common.model.Player;

public class GameController {

	public static Player determinePlayer(String arg, Bead bead) {
		return new HumanPlayer(arg, bead);

	}

	public static void main(String[] args) {
		if (args.length == 2) {
			Player p1 = determinePlayer(args[0], new Bead(Colour.RED));
			Player p2 = determinePlayer(args[1], new Bead(Colour.YELLOW));
			Game g = new Game(p1, p2);
			g.start();
		} else {
			System.out.println("Error! Args length != 2!");
		}
	}
}

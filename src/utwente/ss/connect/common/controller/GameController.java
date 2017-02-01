package utwente.ss.connect.common.controller;

import utwente.ss.connect.common.model.Bead;
import utwente.ss.connect.common.model.Colour;
import utwente.ss.connect.common.model.players.ComputerPlayer;
import utwente.ss.connect.common.model.players.HumanPlayer;
import utwente.ss.connect.common.model.players.Player;
import utwente.ss.connect.common.model.strategies.SmartStrategy;
import utwente.ss.connect.common.model.strategies.Strategy;

public class GameController {

	public static Player determinePlayer(Strategy strat, Bead bead) {
		return new ComputerPlayer(strat, bead);

	}

	public static void main(String[] args) {

		Game game = new Game();
		game.addPlayer(new ComputerPlayer(new SmartStrategy(), new Bead(Colour.YELLOW)));
		game.addPlayer(new HumanPlayer("niek", new Bead(Colour.YELLOW)));
		game.start();

	}
}

package utwente.ss.connect.test;

import static org.junit.Assert.assertTrue;

import org.junit.Before;
import org.junit.Test;

import utwente.ss.connect.common.model.Board;
import utwente.ss.connect.common.model.Game;
import utwente.ss.connect.common.model.players.HumanPlayer;
import utwente.ss.connect.common.model.players.Player;

public class GameTest {

	private Game game;
	private Board board;
	private Player player1;
	private Player player2;
	
	@Before
	public void setUp() {
		game = new Game();
		this.board = game.getBoard();
	}
	
	@Test
	public void getBoardTest() {
		assertTrue(game.getBoard().equals(board));
	}
	
	@Test
	public void addPlayerTest() {
		player1 = new HumanPlayer();
		game.addPlayer(player1);
		
		assertTrue(game.getPlayers().size() == 1);
		
		player2 = new HumanPlayer();
		game.addPlayer(player2);
		
		assertTrue(game.getPlayers().size() == 2);
		
		game.removePlayer(player1);
		
		assertTrue(game.getPlayers().size() == 1);
		
	}
}

package utwente.ss.connect.common.model;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name, Bead bead) {
		super(name, bead);
	}

	@Override
	public String getName() {
		return "ComputerPlayer " + super.getName();
	}

	@Override
	public int[] determineMove(Board board) {
		return null;
	}

}

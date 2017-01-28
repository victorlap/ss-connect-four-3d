package utwente.ss.connect.common.model;

public class ComputerPlayer extends Player {

	public ComputerPlayer(String name) {
		super(name);
	}
	
	@Override
	public String getName() {
		return "ComputerPlayer "+ super.getName();
	}

	public void determineMove() {
		// TODO Auto-generated method stub

	}

}

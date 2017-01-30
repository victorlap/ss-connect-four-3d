package utwente.ss.connect.common.model;

public abstract class Player {
	
	protected String name;
	protected Bead bead;
	
	public Player(String name, Bead bead) {
		setName(name);;
		setBead(bead);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setBead(Bead bead) {
		this.bead = bead;
	}
	
	public Bead getBead(){
		
		return bead;
	}
	
	@Override
	public String toString() {
		return getName() +" : "+ getBead();
	}
	
	public abstract String determineMove(Board board);

}

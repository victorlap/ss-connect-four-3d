package utwente.ss.connect.common.model;

public class Player {
	
	protected String name;
	protected Bead bead;
	
	public Player() {
		this("UNKOWN");
	}
	
	public Player(String name) {
		this(name, new Bead(Colour.EMPTY));
	}
	
	public Player(String name, Bead bead) {
		setName(name);
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
		return getName();
	}
	
	@Override
	public boolean equals(Object o) {
		if(o instanceof Player) {
			Player other = (Player) o;
			return this.getName().equals(other.getName());
		}
		return false;
	}
	
	public String determineMove(Board board) {
		return null;
	}
}

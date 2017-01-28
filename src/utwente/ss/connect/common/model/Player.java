package utwente.ss.connect.common.model;

public class Player {
	
	protected String name;
	protected Colour colour;
	
	public Player() {
		this("UNKOWN");
	}
	
	public Player(String name) {
		setName(name);
		setColour(Colour.EMPTY);
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getName() {
		return name;
	}
	
	public void setColour(Colour colour) {
		this.colour = colour;
	}
	
	public Colour getColour() {
		return colour;
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
}

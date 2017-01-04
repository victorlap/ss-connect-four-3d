package utwente.ss.connect.common.model;

public abstract class Player {
	
	protected String name;
	protected Colour colour;
	
	public Player(String name) {
		setName(name);;
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
		return getName() +" : "+ getColour();
	}
	
	public abstract void determineMove();

}

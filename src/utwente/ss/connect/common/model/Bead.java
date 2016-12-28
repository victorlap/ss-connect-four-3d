package utwente.ss.connect.common.model;

public class Bead {

	private Colour colour;
	
	public Bead(Colour colour){
		
		this.colour = colour;
	}
	
	public Colour getColour(){
		return colour;
	}
	
	public String toString(){
	
		return "" + colour.toString();
		
	}

}

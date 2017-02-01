package utwente.ss.connect.common.model;

public enum Colour {

	YELLOW("\u001B[33m"), RED("\u001B[31m"), EMPTY("\u001B[37m");

	private final String str;

	private Colour(String s) {
		str = s;
	}

	public String toString() {
		return str;
	}
	
	public Colour next(Colour colour) {
		switch(colour) {
			case YELLOW:
				return RED;
			case RED:
				return YELLOW;
			default:
				return EMPTY;

		}
	}
}

package model;

import java.util.List;
import java.util.ArrayList;

public class Encounter {
	public String name;
	public List<Actor> challengers = new ArrayList<Actor>();
	
	/**
	 * Constructor without challengers.
	 * 
	 * @param name
	 */
	public Encounter(String name) {
		this.name = name;
	}

	/**
	 * Constructor with starting challengers.
	 * 
	 * @param name
	 * @param challengers
	 */
	public Encounter(String name, List<Actor> challengers) {
		this(name);
		this.challengers = challengers;
	}
}

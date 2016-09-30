package model;

import java.util.ArrayList;

import com.google.gson.annotations.Expose;

public class Encounter {
	@Expose(serialize = true)
	public String encounterName = null;
	
	@Expose(serialize = true)
	private ArrayList<Actor> actorList = new ArrayList<Actor>();

	/**
	 * Constructor.
	 */
	public Encounter(String name) {
		this.encounterName = name;
	}
	
	public ArrayList<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(ArrayList<Actor> actorList) {
		this.actorList = actorList;
	}
	
}

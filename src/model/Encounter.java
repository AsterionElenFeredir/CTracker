package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class Encounter {
	@Expose(serialize = true)
	public String encounterName = null;
	
	@Expose(serialize = true)
	private List<Actor> actorList = new ArrayList<Actor>();

	/**
	 * Constructor.
	 */
	public Encounter(String name) {
		this.encounterName = name;
	}
	
	public List<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	
}

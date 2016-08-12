package model;

import java.util.ArrayList;
import java.util.List;

public class ActorList {
	/** Liste des acteurs. */
	private List<Actor> actorList = new ArrayList<Actor>();

	public List<Actor> getActorList() {
		return actorList;
	}

	public void setActorList(List<Actor> actorList) {
		this.actorList = actorList;
	}
	
}

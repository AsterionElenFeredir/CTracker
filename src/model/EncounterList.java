package model;

import java.util.ArrayList;
import java.util.List;

import com.google.gson.annotations.Expose;

public class EncounterList {
	@Expose(serialize = true)
	public List<Encounter> encounters = new ArrayList<Encounter>();
	
	public void add(Encounter encounter) {
		encounters.add(encounter);
	}
}

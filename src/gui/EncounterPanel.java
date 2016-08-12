package gui;

import javax.swing.JPanel;

import model.Encounter;

public class EncounterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public Encounter encounter = null;
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 */
	public EncounterPanel(String name) {
		encounter = new Encounter(name);
	}

}

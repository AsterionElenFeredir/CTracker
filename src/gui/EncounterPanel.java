package gui;

import java.awt.Color;
import java.awt.FlowLayout;

import javax.swing.JPanel;

import model.Encounter;

public class EncounterPanel extends JPanel {

	private static final long serialVersionUID = 1L;
	
	public Encounter encounter = null;
	
	public EncounterPanel() {
		setLayout(new FlowLayout(FlowLayout.CENTER, 10, 10));
		setBackground(Color.BLACK);
	}
	
	/**
	 * Constructor.
	 * 
	 * @param name
	 */
	public EncounterPanel(String name) {
		this();
		encounter = new Encounter(name);
	}

	/**
	 * Constructor.
	 * 
	 * @param encounter
	 */
	public EncounterPanel(Encounter encounter) {
		this();
		this.encounter = encounter;
	}
}
